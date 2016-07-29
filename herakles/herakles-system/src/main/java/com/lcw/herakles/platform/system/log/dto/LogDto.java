
package com.lcw.herakles.platform.system.log.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.system.log.enumeration.ELogType;

/**
 * @author chenwulou
 *
 */
public class LogDto extends BasePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment("编号")
    private String id;

    @Comment("日志类型")
    private ELogType type;

    @Comment("日志标题")
    private String title;

    @Comment("操作IP地址")
    private String remoteAddr;

    @Comment("用户代理")
    private String userAgent;

    @Comment("请求URI")
    private String requestUri;

    @Comment("操作方式")
    private String method;

    @Comment("操作提交的数据")
    private String params;

    @Comment("异常信息")
    private String exception;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ELogType getType() {
        return type;
    }

    public void setType(ELogType type) {
        this.type = type;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
