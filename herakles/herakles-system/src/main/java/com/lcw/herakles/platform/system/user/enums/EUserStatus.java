
package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

/**
 * Class Name: EUserStatus
 * 
 * @author chenwulou
 * 
 */
@JsonSerialize(using = PageEnumSerializer.class)
public enum EUserStatus implements PageEnum {

    NULL("", ""), ACTIVE("A", "Active"), INACTIVE("I", "Inactive"), CANCELLED("C", "Cancelled");

    private String code;

    private String text;

    EUserStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

}
