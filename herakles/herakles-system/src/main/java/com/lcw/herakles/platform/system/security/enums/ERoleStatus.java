
package com.lcw.herakles.platform.system.security.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBStrEnum;
import com.lcw.herakles.platform.common.util.DBStrEnumSerializer;


/**
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBStrEnumSerializer.class)
public enum ERoleStatus implements DBStrEnum {

    INEFFECTIVE("0", "待生效"), EFFICIENT("1", "已生效");

    private String code;

    private String text;

    private ERoleStatus(String code, String text) {
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
