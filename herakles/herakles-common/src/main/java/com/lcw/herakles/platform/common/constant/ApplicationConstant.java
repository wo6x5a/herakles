package com.lcw.herakles.platform.common.constant;

/**
 * Class Name: ApplicationConstant Description: application level constants.
 * 
 * @author chenwulou
 * 
 */
public final class ApplicationConstant {

	private ApplicationConstant() {

	}

	public static final String GLOBAL_CONTEXT = "/web/";

	public static final String DEFAULT_CREATE_OPID = "system";

	public static final String X_FORM_ID = "x-form-id";

	public static final String MANUAL_VALIDATE = "manualValidate";

	/** Log discriminator **/
	public static final String LOG_USER_NAME = "userName";
	public static final String LOG_SESSION_ID = "sessionId";

	/**
	 * 默认最大支持导出数据条数
	 */
	public static final int EXPORT_DATA_MAX_SIZE = 50000;

	/**
	 * encoding
	 */
	public static final String UTF_8 = "UTF-8";
	public static final String GB2312 = "GB2312";

	/**
	 * expend excel report
	 */
	public static final String REPORT_DATA = "data";
	public static final String REPORT_FILE_NAME = "fileName";
	public static final String REPORT_TEMP_PATH = "tempPath";

	/**
	 * MD5默认迭代次数
	 */
	public static final int ITERATIONS = 3;

}
