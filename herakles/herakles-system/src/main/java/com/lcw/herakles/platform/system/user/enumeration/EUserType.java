package com.lcw.herakles.platform.system.user.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum EUserType implements PageEnum {
  
    ALL("ALL","全部"),
    PERSON("P", "个人");
    
    private String code;
    private String text;
    
    private EUserType(String code, String text){
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
