package com.lcw.herakles.platform.system.log.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum ELogType implements PageEnum{

    
    NULL("",""),
    PRODUCT("PRODUCT","产品模块");
  
    ELogType(String code, String text) {
        this.code = code;
        this.text = text;
    }
  
    private String code;
  
    private String text;
  
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
