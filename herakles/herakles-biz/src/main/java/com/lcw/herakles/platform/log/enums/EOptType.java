package com.lcw.herakles.platform.log.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

/**
 * 操作
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = PageEnumSerializer.class)
public enum EOptType implements PageEnum{

    
    ALL("","全部"),
    INSERT("INSERT","插入"),
	UPDATE("UPDATE","修改"),
	DELETE("DELETE","删除");
  
    EOptType(String code, String text) {
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
