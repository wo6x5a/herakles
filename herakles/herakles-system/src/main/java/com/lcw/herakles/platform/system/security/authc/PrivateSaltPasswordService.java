
package com.lcw.herakles.platform.system.security.authc;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.HexFormat;
import org.apache.shiro.util.SimpleByteSource;

/**
 * Class Name: PrivateSaltPasswordService
 * Description: TODO
 * 
 * @author chenwulou
 * 
 */
public class PrivateSaltPasswordService extends DefaultPasswordService {

    private static final String PRIVATE_SALT = "#*6x5_Lcw_Salt_//@!";

    private static final int LEGACY_MD5_LEN = 32;

    private HashService md5HashService;

    private HashFormat hexHashFormat;

    private HashService defaultHashService;

    private HashFormat defaultHashFormat;

    public PrivateSaltPasswordService() {
        super();
        HashService hashService = getHashService();
        if (hashService instanceof DefaultHashService) {
            ((DefaultHashService) hashService).setPrivateSalt(new SimpleByteSource(PRIVATE_SALT));
        }
        DefaultHashService hashServiceInstance = new DefaultHashService();
        hashServiceInstance.setHashAlgorithmName("MD5");
        this.md5HashService = hashServiceInstance;
        hexHashFormat = new HexFormat();
        this.defaultHashService = super.getHashService();
        defaultHashFormat = super.getHashFormat();
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String saved) {
        if (saved.length() == LEGACY_MD5_LEN) {// MD5
            super.setHashService(md5HashService);
            super.setHashFormat(hexHashFormat);
            boolean match = super.passwordsMatch(submittedPlaintext, saved);
            restoreHashStrategy();
            if (match) {
                throw new LegacyPasswordMatchException("Legacy md5 format password matched.");
            }
            return match;
        } else if (saved.length() > LEGACY_MD5_LEN) {// shiro format
            return super.passwordsMatch(submittedPlaintext, saved);
        } else {// plain text
            String plainText = "";
            if (submittedPlaintext instanceof char[]) {
                plainText = new String((char[]) submittedPlaintext);
            } else if (submittedPlaintext instanceof String) {
                plainText = (String) submittedPlaintext;
            } else {
                throw new IllegalArgumentException("Not supported parameter type for submittedPlaintext: "
                        + submittedPlaintext.getClass().getName());
            }
            boolean match = plainText.equals(saved);
            if (match) {
                throw new LegacyPasswordMatchException("Legacy plaintext format password matched.");
            }
        }
        return false;
    }

    private void restoreHashStrategy() {
        super.setHashService(defaultHashService);
        super.setHashFormat(defaultHashFormat);
    }

    /**
     * @param hashInterations
     *            Set hashInterations value
     */
    public void setHashIterations(int hashIterations) {
        DefaultHashService hashServiceInstance = (DefaultHashService) super.getHashService();
        hashServiceInstance.setHashIterations(hashIterations);
    }

}
