package com.lcw.herakles.platform.log.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

/**
 * 操作
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBEnumSerializer.class)
public enum EOptType implements DBEnum{

    
    ALL(0,"全部"),
    INSERT(1,"插入"),
	UPDATE(2,"修改"),
	DELETE(3,"删除");
  
    EOptType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
  
    private Integer code;
  
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
    public Integer getCode() {
      return code;
    }
  
    @Override
    public void setCode(Integer code) {
      this.code = code;
    }
}
