
package com.lcw.herakles.platform.system.log.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.log.enumeration.ELogType;
import com.lcw.herakles.platform.system.log.enumeration.converter.ELogTypeConverter;

@Entity
@Table(name = "SYS_LOG")
@EntityListeners(IdInjectionEntityListener.class)
public class LogPo extends BasePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @Comment("编号")
    private String id;

    @Column(name = "TYPE")
    @Comment("日志类型")
    @Convert(converter = ELogTypeConverter.class)
    private ELogType type;

    @Column(name = "TITLE")
    @Comment("日志标题")
    private String title;

    @Column(name = "REMOTE_ADDR")
    @Comment("操作IP地址")
    private String remoteAddr;

    @Column(name = "USER_AGENT")
    @Comment("用户代理")
    private String userAgent;

    @Column(name = "REQUEST_URI")
    @Comment("请求URI")
    private String requestUri;

    @Column(name = "METHOD")
    @Comment("操作方式")
    private String method;

    @Column(name = "PARAMS")
    @Comment("操作提交的数据")
    private String params;

    @Column(name = "EXCEPTION")
    @Comment("异常信息")
    private String exception;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ELogType getType() {
        return type;
    }

    public void setType(ELogType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
