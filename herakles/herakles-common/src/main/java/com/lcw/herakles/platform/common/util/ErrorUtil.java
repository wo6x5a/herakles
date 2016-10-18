package com.lcw.herakles.platform.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.lang3.StringUtils;

import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.exception.BizServiceException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author chenwulou
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorUtil {

	public static String getExceptionStack(Exception ex, int length) {
		String errMsg = getExceptionStack(ex);
		if(length > 0 && StringUtils.isNotBlank(errMsg)){
			if(length < errMsg.length()){
				errMsg = errMsg.substring(0, length);
			}
		}
		return errMsg;
	}
	
	public static String getExceptionStack(Exception ex){
		String errMsg = null;
		if(ex!=null){
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintStream pts = new PrintStream(bos);
				ex.printStackTrace(pts);
				errMsg = bos.toString();
				bos.close();
				pts.close();
			} catch (Exception e) {
				errMsg = ex.toString();
			}
		}
		return errMsg;
	}
	
	public static void throwBizException(EErrorCode errorCode, Object... args){
		if(args != null){
			errorCode.setArgs(args);
		}
		throw new BizServiceException(errorCode);
	}

}
