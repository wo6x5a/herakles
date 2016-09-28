	
package com.lcw.herakles.platform.system.security.authc;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.constant.ResultCode;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.util.MessageUtil;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.system.security.SecurityContext;

/**
 * Class Name: SessionTimeoutAuthenticationFilter
 * Description: TODO
 * @author chenwulou
 *
 */
public class SessionTimeoutAuthenticationFilter extends FormAuthenticationFilter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTimeoutAuthenticationFilter.class);
    
    public static final String SESSION_TIMEOUT = SessionTimeoutAuthenticationFilter.class + "_session_timeout";
    
    private static final String SESSION_TIMEOUT_MSG = "error.common.session_timeout";
    
    //only part of session id is enough for identification
    private static final int SESSION_ID_SECTION_LEN = 15;

    private static final String DATE_FORMAT = "MM-dd HH:mm:ss";
    
    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        if(WebUtil.isAjaxRequest(req)){
            ObjectMapper objectMapper = new ObjectMapper();
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); 
            ResultDto error = new ResultDto();
            error.setCode(ResultCode.SESSION_TIME_OUT);
            error.setMessage(MessageUtil.getMessage(SESSION_TIMEOUT_MSG));
            objectMapper.writeValue(response.getWriter(), error);
            LOGGER.debug("session time out for ajax request:{}", req.getRequestURI());
        }else{
            LOGGER.debug("session time out for request:{}", req.getRequestURI());
            req.getSession().setAttribute(SESSION_TIMEOUT, true);
            redirectToLogin(request, response);
        }
        HttpSession session = req.getSession(false);
        if (session != null) {
			LOGGER.debug("session time out with id: {}, is sesion new:{}, started: {}, last accessed: {}, request headers: {}",
					session.getId(),
					session.isNew(),
					DateFormatUtils.format(session.getCreationTime(), DATE_FORMAT),
					DateFormatUtils.format(session.getLastAccessedTime(), DATE_FORMAT),
					getHeaderString(request));
        } else {
        	LOGGER.debug("session time out, no session available for current request");
        }
    }
    
    private String getHeaderString(ServletRequest request ){
        HttpServletRequest req = (HttpServletRequest)request;
        StringBuilder sb = new StringBuilder();
        Enumeration<?> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            sb.append("[").append(headerName).append("=").append(req.getHeader(headerName)).append("]");
        }
        return sb.toString();
    }
    
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception { // NOSONAR
        boolean res = super.onPreHandle(request, response, mappedValue);
        if(SecurityContext.getInstance().isAuthenticated()){
            MDC.put(ApplicationConsts.LOG_USER_NAME, SecurityContext.getInstance().getCurrentUser().getNickName());
        }
        String sessionId = ((HttpServletRequest)request).getSession(false).getId();
        if(sessionId!=null){
            MDC.put(ApplicationConsts.LOG_SESSION_ID, sessionId.substring(sessionId.length()-SESSION_ID_SECTION_LEN));
        }
        return res;
    }
    
    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing)
            throws ServletException, IOException {
        MDC.remove(ApplicationConsts.LOG_USER_NAME);
        MDC.remove(ApplicationConsts.LOG_SESSION_ID);
        super.cleanup(request, response, existing);
    }
    
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception { // NOSONAR
        HttpServletRequest req = (HttpServletRequest)request;
        LOGGER.info("{} login complete, session ID: {} ", subject.getPrincipal(), subject.getSession().getId());
        StringBuilder sb = new StringBuilder();
        Enumeration<?> attriNames = req.getAttributeNames();
        while (attriNames.hasMoreElements()) {
            String attriName = (String) attriNames.nextElement();
            sb.append("[").append(attriName).append("=").append(req.getAttribute(attriName)).append("]");
        }
        LOGGER.info("session attributes: {}", sb);
        return super.onLoginSuccess(token, subject, request, response);
    }
    
}
