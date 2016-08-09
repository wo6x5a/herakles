package com.lcw.herakles.platform.common.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum ECurrType implements PageEnum {
  
    ALL("ALL", "全部"),
    CNY("CNY","人民币"),
    USD("USD","美元"),
    HKD("HKD","港币");
  
    private String code;
    
    private String text;
    
    private ECurrType(String code, String text) {
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
