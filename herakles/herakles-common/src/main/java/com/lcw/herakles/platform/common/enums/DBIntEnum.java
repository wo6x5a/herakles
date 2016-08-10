package com.lcw.herakles.platform.common.enums;

/**
 * @author chenwulou
 *
 */
public interface DBIntEnum {

	/**
	 * Description: get the text for display
	 *
	 * @return
	 */
	String getText();

	/**
	 * Description: set the text for display
	 *
	 * @param text
	 */
	void setText(String text);

	/**
	 * Description: get the code of the enum
	 *
	 * @return
	 */
	Integer getCode();

	/**
	 * Description: set the code of the enum
	 *
	 * @param code
	 */
	void setCode(Integer code);

	/**
	 * Description: set the name of the enum
	 *
	 * @return
	 */
	String name();
}
