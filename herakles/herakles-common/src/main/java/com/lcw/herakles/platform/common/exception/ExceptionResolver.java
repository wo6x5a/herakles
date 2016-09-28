package com.lcw.herakles.platform.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.util.MessageUtil;
import com.lcw.herakles.platform.common.util.web.WebUtil;
import com.lcw.herakles.platform.common.validation.ValidateException;

/**
 * Class Name: ExceptionResolver
 * <p>
 * Description: the <code>ValidateException</code> handler<br>
 * the validation from service will be wrapped into <code>ValidateException</code>, then the handler will catch the
 * exception and return the errors into view
 * 
 * @author chenwulou
 * 
 */
@SuppressWarnings("deprecation")
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    BindingResultExceptionHandler bindingResultExceptionHandler;

    @Autowired
    BeanValidatorExceptionHandler beanValidatorExceptionHandler;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler, final Exception ex) {
        if (WebUtil.isAjaxRequest(request)) {
            try {
                String formId = request.getHeader(ApplicationConsts.X_FORM_ID);
                Locale locale = request.getLocale();
                ObjectMapper objectMapper = new ObjectMapper();
                response.setContentType("application/json;charset=UTF-8");
                ResultDto error = getErrorDto(ex, handler, formId, locale);
                if(error.isNonBizError()){
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); 
                }else{
                    response.setStatus(HttpStatus.OK.value());
                }
                PrintWriter writer = response.getWriter();
                objectMapper.writeValue(response.getWriter(), error);
                writer.flush();
            } catch (IOException ie) {
                LOGGER.error("Failed to serialize the object to json for exception handling.{}", ie);
            }
            return new ModelAndView();
        } else {
            response.setContentType("text/html;charset=UTF-8");
            ModelAndView mav = new ModelAndView();
            mav.addObject("errorMessage", ExceptionUtils.getStackTrace(ex));
            if(ex instanceof AuthorizationException){
                LOGGER.warn("AuthorizationException handled (non-ajax style):{}", ex);
                mav.setViewName("error/access_denied");
            }else{
                LOGGER.error("Unknown exception handled (non-ajax style):{}", ex);
                mav.setViewName("error/404");
            }
            return mav;
        }
    }

    /**
     * 
     * Description: return the error message to front-end
     * 
     * @param e
     * @param handler
     * @param formId
     * @param locale
     * @return
     */
    private ResultDto getErrorDto(final Exception ex, final Object handler, final String formId, final Locale locale) {
        ResultDto error = new ResultDto();
        if (ex instanceof ValidateException) {
            return bindingResultExceptionHandler.buildErrorDto(ex, handler, formId);
        } else if (ex instanceof ConstraintViolationException) {
            return beanValidatorExceptionHandler.buildErrorDto(ex, handler, formId);
        } else if (ex instanceof BizServiceException) {
            BizServiceException bizEx = (BizServiceException) ex;
            DisplayableError errorCode = bizEx.getError();
            String msg = MessageUtil.getMessage(errorCode.getDisplayMsg(),locale, errorCode.getArgs());
            if (msg == null) {
                msg = errorCode.getDisplayMsg();
            }
            if(errorCode.isBizError()){
                error = ResultDtoFactory.toBizError(msg,ex);
            }else{
                error = ResultDtoFactory.toCommonError(bizEx);
            }
            LOGGER.debug("BizServiceException handled:", ex);
        } else if (ex instanceof MaxUploadSizeExceededException) {
            error = ResultDtoFactory.toNack("文件大小必须小于2M，请重新上传");
        } else if (ex instanceof HibernateOptimisticLockingFailureException) {
            LOGGER.info("HibernateOptimisticLockingFailureException handled:", ex);
            error = ResultDtoFactory.toNack("您正在操作的记录已经在您操作之前被其他用户修改，请刷新页面后重试！");
        } else if (ex instanceof AuthorizationException){
            LOGGER.warn("AuthorizationException handled:", ex);
            error = ResultDtoFactory.toCommonError(MessageUtil.getMessage("error.common.unauthz"));
        }
        else {
            error = ResultDtoFactory.toCommonError( ex);
            LOGGER.error("Unknown exception handled:{}", ex);
        }
        return error;
    }
}
