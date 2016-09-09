package com.lcw.herakles.platform.system.security.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

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

    @Override
    public void setUsername(String username) {
        try {
            username = ConfigTools.decrypt(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        super.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        try {
            password = ConfigTools.decrypt(password);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        super.setPassword(password);
    }

    @Override
    public void setUrl(String url) {
        try {
            url = ConfigTools.decrypt(url);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
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
