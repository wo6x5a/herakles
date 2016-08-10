	
package com.lcw.herakles.platform.common.enums;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EnumOption implements Serializable {

    private String code;
    
    private String text;

    public EnumOption(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
	public EnumOption(DBIntEnum dbIntEnum) {
		this.code = dbIntEnum.name();
		this.text = dbIntEnum.getText();
	}

	public EnumOption(DBStrEnum dbStrEnum) {
		this.code = dbStrEnum.name();
		this.text = dbStrEnum.getText();
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return code;
    }

}
