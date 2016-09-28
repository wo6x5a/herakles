
package com.lcw.herakles.platform.system.security;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.constant.CacheConsts;
import com.lcw.herakles.platform.common.util.AppConfigUtil;
import com.lcw.herakles.platform.common.util.ApplicationContextUtil;
import com.lcw.herakles.platform.system.security.authc.ShiroJdbcRealm;
import com.lcw.herakles.platform.system.user.dto.UserDto;
import com.lcw.herakles.platform.system.user.service.UserPasswdService;
import com.lcw.herakles.platform.system.user.service.UserService;

/**
 * Class Name: SecurityContext Description: TODO
 * 
 * @author chenwulou
 * 
 */
public class BaseSecurityContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseSecurityContext.class);

	/**
	 * Description: 获取创建人编号,如果没有就默认.
	 * 
	 * @return
	 */
	public String getCurrentOperatorId() {
        Subject subject = getSubject();
        if (subject == null) {
            return null;
        }
        String userId = (String) subject.getSession().getAttribute(CacheConsts.USER_ID_KEY);
	    return (StringUtils.isBlank(userId) ? ApplicationConsts.DEFAULT_CREATE_OPID : userId);
	}

	/**
	 * Description: 获取当前用户的基本信息。
	 * 
	 * @return
	 */
	public UserDto getCurrentUser() {
		Subject subject = getSubject();
		if (subject == null) {
			return null;
		}
		return (UserDto) subject.getSession().getAttribute(CacheConsts.USER_KEY);
	}

	/**
	 * Description: 是否已登录
	 * 
	 * @return
	 */
	public boolean isAuthenticated() {
		Subject subject = getSubject();
		if (subject == null) {
			return false;
		}
		return getSubject().isAuthenticated();
	}

	/**
	 * Description: 登录验证该用户名, 登陆登陆成功后会创建新的会话。
	 * 
	 * @param userId
	 * @param password
	 * @return the new session
	 * @throws IncorrectCredentialsException
	 *             密码错误
	 * @throws LockedAccountException
	 *             登陆失败次数过多导致账户被锁
	 */
	public static Session login(String userName, String password, boolean rememberMe)
			throws IncorrectCredentialsException, LockedAccountException {
		long start = System.currentTimeMillis();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password, rememberMe);
		Subject currentUser = SecurityUtils.getSubject();
		// This is to prevent session fixation attack, see:
		// https://issues.apache.org/jira/browse/SHIRO-170
		currentUser.getSession().stop();
		// this will create a new session by default in applications that allow
		// session state:
		currentUser.login(token);
		Session session = currentUser.getSession();
		LOGGER.debug("User {} login successfully, session id {}", userName, session.getId());
		UserService userService = ApplicationContextUtil.getBean(UserService.class);
		UserDto userDto = userService.findByNickName(userName);
        session.setAttribute(CacheConsts.USER_KEY, userDto);
        session.setAttribute(CacheConsts.USER_ID_KEY, userDto.getUserId());
		encryptUserPwd(userDto.getUserId());
		long end = System.currentTimeMillis();
		LOGGER.debug("login() completed for user {}, total time spent: {}ms", userName, end - start);
		return session;
	}

	private static void encryptUserPwd(String userId) {
		UserPasswdService userPasswdService = ApplicationContextUtil.getBean(UserPasswdService.class);
		userPasswdService.encryptUserPwd(userId);
	}

	/**
	 * 登出当前用户
	 */
	public static void logout() {
		getSubject().logout();
	}

	/**
	 * Description: 清除指定用户的授权信息缓存。
	 * 
	 * @param userId
	 */
	public static void clearAuthzCache(String userName) {
		RealmSecurityManager sm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		for (Realm realm : sm.getRealms()) {
			if (realm instanceof ShiroJdbcRealm) {
				ShiroJdbcRealm jdbcRealm = (ShiroJdbcRealm) realm;
				SimplePrincipalCollection spc = new SimplePrincipalCollection(userName, realm.getName());
				jdbcRealm.clearAuthorizationCache(spc);
			}
		}
		LOGGER.info("Authorization cache cleared for user: {}", userName);
	}

	/**
	 * Description: 清除指定用户列表的授权信息缓存。
	 * 
	 * @param users
	 */
	public static void clearAuthzCache(List<String> users) {
		for (String user : users) {
			clearAuthzCache(user);
		}
	}

	/**
	 * Description: 清除所有用户的授权信息缓存。
	 * 
	 * @param users
	 */
	public static void clearAllAuthzCache() {
		CacheManager cm = (CacheManager) ((CachingSecurityManager) SecurityUtils.getSecurityManager())
				.getCacheManager();
		cm.getCache(AppConfigUtil.getConfig(CacheConsts.CACHE_NAME_AUTHZ)).clear();
	}

	/**
	 * Description: 清除指定用户的认证信息缓存。
	 * 
	 * @param userId
	 */
	public static void clearAuthcCache(String userName) {
		RealmSecurityManager sm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		for (Realm realm : sm.getRealms()) {
			if (realm instanceof ShiroJdbcRealm) {
				ShiroJdbcRealm jdbcRealm = (ShiroJdbcRealm) realm;
				SimplePrincipalCollection spc = new SimplePrincipalCollection(userName, realm.getName());
				jdbcRealm.clearAuthenticationCache(spc);
			}
		}
	}

	/**
	 * Description: 清除指定用户list的认证信息缓存。
	 * 
	 * @param users
	 */
	public static void clearAuthcCache(List<String> users) {
		for (String user : users) {
			clearAuthcCache(user);
		}
	}

	/**
	 * Description: 清除所有用户的认证信息缓存。
	 * 
	 * @param users
	 */
	public static void clearAllAuthcCache() {
		CacheManager cm = (CacheManager) ((CachingSecurityManager) SecurityUtils.getSecurityManager())
				.getCacheManager();
		cm.getCache(AppConfigUtil.getConfig(CacheConsts.CACHE_NAME_AUTHC)).clear();
	}

	/**
	 * Description: 清除当前用户的授权信息缓存
	 * 
	 */
	public void clearCurrentAuthzCache() {
		clearAuthzCache(getSubject().getPrincipal().toString());
	}

	/**
	 * Description: 验证当前用户是否拥有该权限。
	 * 
	 * @param permission
	 * @return
	 */
	public boolean hasPermission(String permission) {
		Subject subject = getSubject();
		return subject == null ? false : subject.isPermitted(permission);
	}

	/**
	 * Description: 验证当前用户是否拥有所有以下权限。
	 * 
	 * @param permissions
	 * @return
	 */
	public boolean hasAllPermissions(String... permissions) {
		Subject subject = getSubject();
		return subject == null ? false : subject.isPermittedAll(permissions);
	}

	/**
	 * Description: 验证当前用户是否拥有以下任意一个权限
	 * 
	 * @param permissions
	 * @return
	 */
	public static boolean hasAnyPermission(String[] permissions) {
		Subject subject = getSubject();
		if (subject != null && permissions != null) {
			for (int i = 0; i < permissions.length; i++) {
				String permission = permissions[i];
				if (permission != null && subject.isPermitted(permission.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查是否有权限，若无则抛出异常。
	 * 
	 * @see org.apache.shiro.subject.Subject#checkPermission(String permission)
	 * @param permission
	 * @throws AuthorizationException
	 */
	public void checkPermission(String permission) throws AuthorizationException {
		Subject subject = getSubject();
		if (subject == null) {
			throw new AuthorizationException("No permission as there is no subject bound.");
		}
		subject.checkPermission(permission);
	}

	/**
	 * Description: 验证当前用户是否属于以下所有角色。请通过权限而不是角色做判断，比如hasPermission。
	 * 
	 * @param roles
	 * @return
	 */
	@Deprecated
	public boolean hasAllRoles(Collection<String> roles) {
		return getSubject().hasAllRoles(roles);
	}

	/**
	 * Description: 验证当前用户是否属于以下任意一个角色。请通过权限而不是角色做判断，比如hasPermission。
	 * 
	 * @param roleNames
	 * @return
	 */
	@Deprecated
	public boolean hasAnyRoles(Collection<String> roleNames) {
		Subject subject = getSubject();
		if (subject != null && roleNames != null) {
			for (String role : roleNames) {
				if (role != null && subject.hasRole(role)) {
					return true;
				}
			}
		}
		return false;
	}

	private static Subject getSubject() {
		try {
			return SecurityUtils.getSubject();
		} catch (Exception e) {
			LOGGER.warn("Failed to get Subject, maybe user is not login or session is lost:", e);
			return null;
		}
	}

}
