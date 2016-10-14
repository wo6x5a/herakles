package com.lcw.herakles.platform.system.security.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.lcw.herakles.platform.common.util.EndecryptUtil;

/**
 * @author chenwulou
 *
 */
public class DecryptDruidSource extends DruidDataSource {

    /**
     * 
     */
    private static final long serialVersionUID = 2229458728075515162L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptDruidSource.class);

    private static final String DB_AES_KEY = "HdiDnPCCXLjija+1lQkSnw==";

    @Override
    public void setUsername(String username) {
        username = EndecryptUtil.decryptAes(username, DB_AES_KEY);
        super.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        password = EndecryptUtil.decryptAes(password, DB_AES_KEY);
        super.setPassword(password);
    }

    @Override
    public void setUrl(String url) {
        url = EndecryptUtil.decryptAes(url, DB_AES_KEY);
        super.setUrl(url + "?useUnicode=true&characterEncoding=utf-8&useSSL=false");
    }

    public static void main(String[] args) {
        try {
            System.out.println(ConfigTools.encrypt("123456"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
