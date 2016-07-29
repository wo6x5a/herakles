package com.lcw.herakles.platform.common.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum EFlagType implements PageEnum {
  
    ALL("ALL","全部"),
    YES("Y","是"),
    NO("N","否"),
    UNKNOW("U","未知");

    private String code;

    private String text;
    
    private EFlagType(String code, String text){
        this.code = code;
        this.text = text;
    }
  
    @Override
    public String getText() {
      return text;
    }
  
    @Override
    public void setText(String text) {
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

}
