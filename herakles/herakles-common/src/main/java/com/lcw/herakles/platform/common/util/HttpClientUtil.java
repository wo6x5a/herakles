package com.lcw.herakles.platform.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.constant.ApplicationConstant;

/**
 * HTTP client util
 * 
 * @author chenwulou
 *
 */
public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private final static HttpClientUtil instance = new HttpClientUtil();

    public static HttpClientUtil getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        String url = "http://localhost:8080/herakles-web/web/system/file/form/template/write";
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "的撒打算打算");
        params.put("value", "打算打算是滴是滴打");
        HttpClientUtil.getInstance().post(url, params);
    }

    /**
     * 发送 post请求.
     */
    public String post(String url, Map<String, String> params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> formparams = Lists.newArrayList();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            formparams.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            UrlEncodedFormEntity uefEntity =
                    new UrlEncodedFormEntity(formparams, ApplicationConstant.UTF_8);
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, ApplicationConstant.UTF_8);
            }
            response.close();

        } catch (IOException e) {
            LOGGER.error("HttpClientUtil.post,{}", e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOGGER.error("HttpClientUtil.post,{}", e);
            }
        }
        return null;
    }

}
