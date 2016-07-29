
package com.lcw.herakles.platform.demo.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

/**
 * Class Name: EProductCagetory
 * 
 * @author chenwulou
 * 
 */
@JsonSerialize(using = PageEnumSerializer.class)
public enum EProductCagetory implements PageEnum {

    BIRDS("B", "Birds"), FISH("F", "Fish"), DOGS("D", "Dogs"), REPTILES("R", "Reptiles"), CATS("C", "Cats");

    private String code;

    private String text;

    EProductCagetory(String code, String text) {
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
