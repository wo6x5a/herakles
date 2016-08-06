package com.lcw.herakles.platform.system.security.authc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.system.user.enumeration.EUserStatus;

/**
 * Class Name: SSOJdbcReam Description: Extending {@link JdbcRealm} to incorporate SSO.
 * 
 * @see org.apache.shiro.realm.jdbc.JdbcRealm
 * @author chenwulou
 * 
 */

public class ShiroJdbcRealm extends JdbcRealm {

    /**
     * The query that needs to be performed when login fails.
     */
    private String loginFailureQuery;
    /**
     * The query that needs to be performed when login succeeds.
     */
    private String loginSuccessQuery;
    /**
     * The query used to retrieve password policy related information.
     */
    private String passwordPolicyQuery;

    /**
     * The query used to retrieve the last login failure time. The returned timestamp should follow the format
     * {@value #LAST_LOGIN_FAIL_DATE_FORMAT}
     */
    private String lastLoginFailureTsQuery;
    /**
     * The query used to reset the failure count after a given time.
     */
    private String resetLoginFailureQuery;

    private int maxLoginFailureCount = 5;

    /**
     * the time(minutes) an account will be locked after too many login failure
     */
    private int lockTime = 30;

    private String passwordMigrateQuery;

    private PasswordService passwordService;

    /**
     * date format used to format the date or timestamp column of last login failure in DB.
     */
    private static final String LAST_LOGIN_FAIL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroJdbcRealm.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        checkLoginFailure(token.getPrincipal().toString());
        AuthenticationInfo info = super.doGetAuthenticationInfo(token);

        return info;
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
            throws AuthenticationException {
        long start = System.currentTimeMillis();
        try {
            super.assertCredentialsMatch(token, info);
            executeUpdate(loginSuccessQuery, new String[] { token.getPrincipal().toString() });
            long end = System.currentTimeMillis();
            LOGGER.debug("assertCredentialsMatch completed for user {}, total time spent: {}ms", token.getPrincipal(),
                    end - start);
        } catch (IncorrectCredentialsException ice) {
            executeUpdate(loginFailureQuery, new String[] { token.getPrincipal().toString() });
            throw ice;
        } catch (LegacyPasswordMatchException ex) {
            executeUpdate(passwordMigrateQuery, new String[] { passwordService.encryptPassword(token.getCredentials()),
                    token.getPrincipal().toString() });
            LOGGER.info("Legacy password migrated for user:{}", token.getPrincipal().toString());
        }
    }

    /**
     * Description: 暴露清除cache接口，方便运行时更改授权信息
     * 
     */
    public void clearAuthorizationCache(PrincipalCollection principles) {
        super.clearCachedAuthorizationInfo(principles);
    }

    /**
     * Description: 暴露清除cache接口，方便运行时更改认证信息
     * 
     */
    public void clearAuthenticationCache(PrincipalCollection principles) {
        super.clearCachedAuthenticationInfo(principles);
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        return principals.getPrimaryPrincipal();
    }

    protected void checkLoginFailure(String userName) {
        Object[] passwordPolicy = executeSelect(passwordPolicyQuery, new String[] { userName }, 2);
        if (passwordPolicy == null) { // user not exist
            final String message = "Authentication failed for unknown user [" + userName + "]";
            throw new UnknownAccountException(message);
        }
        if (passwordPolicy[0] != null) {
            int loginFailureCount = NumberUtils.toInt(passwordPolicy[0].toString());
            if (loginFailureCount >= maxLoginFailureCount) {
                if (!hasLockTimePassed(userName)) {
                    final String message = "Authentication failed after " + loginFailureCount
                            + " consecutive attempts for user [" + userName + "]";
                    LOGGER.debug(message);
                    throw new LockedAccountException(message);
                } else {
                    executeUpdate(resetLoginFailureQuery, new String[] { userName });
                }
            }
        }
        if (!EUserStatus.ACTIVE.getCode().equals(passwordPolicy[1])) {
            final String message = "Authentication failed for inactive user [" + userName + "]";
            LOGGER.error(message);
            throw new DisabledAccountException(message);
        }
    }

    protected boolean hasLockTimePassed(String userName) {
        Object[] result = executeSelect(lastLoginFailureTsQuery, new String[] { userName }, 1);
        if (result == null) {
            final String message = "Authentication failed for unknown user [" + userName + "]";
            throw new UnknownAccountException(message);
        }
        if (result.length == 0 || result[0] == null) {// it is supposed to only happen when the column is null for a
                                                      // dirty record
            return true;
        }
        Date date = null;
        try {
            date = DateUtils.parseDate(result[0].toString(), new String[] { LAST_LOGIN_FAIL_DATE_FORMAT });
            return DateUtils.addMinutes(date, lockTime).before(new Date());
        } catch (ParseException e) {
            throw new AuthenticationException("Incorrect date format for the lastLoginFailureQuery!", e);
        }
    }

    protected Object[] executeSelect(String query, String[] params, int resultSize) {
        return executeQuery(query, params, false, resultSize);
    }

    protected Object[] executeUpdate(String query, String[] params) {
        return executeQuery(query, params, true, 0);
    }

    protected Object[] executeQuery(String query, String[] params, boolean update, int resultSize) {
        if (query == null) {
            return null;
        }

        long start = System.currentTimeMillis();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(query);
            if (ArrayUtils.isNotEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1, params[i]);
                }
            }
            if (update) {
                ps.executeUpdate();
                LOGGER.info("complete update==============");
            } else {
                rs = ps.executeQuery();

                if (rs.next()) {
                    Object[] result = new Object[resultSize];
                    for (int i = 0; i < resultSize; i++) {
                        result[i] = rs.getObject(i + 1);
                    }
                    return result;
                }
            }
        } catch (Exception e) {
            final String message = "There was an error while executing query [" + query + "]";
            LOGGER.error(message, e);
            throw new AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
        long end = System.currentTimeMillis();
        LOGGER.debug("login query completed({}), total time spent: {}ms", query, (end - start));
        return null;
    }

    public String getLoginFailureQuery() {
        return loginFailureQuery;
    }

    public void setLoginFailureQuery(String loginFailureQuery) {
        this.loginFailureQuery = loginFailureQuery;
    }

    public String getLoginSuccessQuery() {
        return loginSuccessQuery;
    }

    public void setLoginSuccessQuery(String loginSuccessQuery) {
        this.loginSuccessQuery = loginSuccessQuery;
    }

    public String getPasswordPolicyQuery() {
        return passwordPolicyQuery;
    }

    /**
     * The query used to retrieve password policy related information.
     */
    public void setPasswordPolicyQuery(String passwordPolicyQuery) {
        this.passwordPolicyQuery = passwordPolicyQuery;
    }

    public int getMaxLoginFailureCount() {
        return maxLoginFailureCount;
    }

    public void setMaxLoginFailureCount(int maxLoginFailureCount) {
        this.maxLoginFailureCount = maxLoginFailureCount;
    }

    public String getLastLoginFailureTsQuery() {
        return lastLoginFailureTsQuery;
    }

    /**
     * The query used to retrieve the last login failure time. The returned timestamp should follow the format
     * {@value #LAST_LOGIN_FAIL_DATE_FORMAT}
     * 
     * @param lastLoginFailureTsQuery
     */
    public void setLastLoginFailureTsQuery(String lastLoginFailureTsQuery) {
        this.lastLoginFailureTsQuery = lastLoginFailureTsQuery;
    }

    public int getLockTime() {
        return lockTime;
    }

    /**
     * the time(minutes) an account will be locked after too many login failure
     */
    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * The query used to migrate the legacy MD5 or plaintext password to new format.
     * 
     * @param passwordMigrateQuery
     *            Set passwordMigrateQuery value
     */
    public void setPasswordMigrateQuery(String passwordMigrateQuery) {
        this.passwordMigrateQuery = passwordMigrateQuery;
    }

    /**
     * @param passwordService
     *            Set passwordService value
     */
    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public String getResetLoginFailureQuery() {
        return resetLoginFailureQuery;
    }

    public void setResetLoginFailureQuery(String resetLoginFailureQuery) {
        this.resetLoginFailureQuery = resetLoginFailureQuery;
    }

}
