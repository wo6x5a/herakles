package com.lcw.herakles.platform.system.security.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.lcw.herakles.platform.common.util.EndecryptUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */

@Slf4j
public class DecryptDruidSource extends DruidDataSource {

    /**
     * 
     */
    private static final long serialVersionUID = 2229458728075515162L;

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
            System.out.println(EndecryptUtil.encryptAes("jdbc:mysql://172.16.31.119:3306/herakles", DB_AES_KEY));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
