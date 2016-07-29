package com.lcw.herakles.platform.system.dict.dto;

import com.lcw.herakles.platform.system.dict.util.SystemDictUtil;

public class DynamicOption {

    private String id;
  
    private String category;
    
    private String code;
  
    private String text;
  
    private String longText;
  
    private String enabled;
  
    private String parentCode;
  
    private String leaf;
  
    private Long order;
  
    private String fullText;
  
    public DynamicOption() {
  
    }
  
    public DynamicOption(String category, String code, String text) {
        this.category = category;
        this.code = code;
        this.setText(text);
    }
  
    public String getFullText() {
        fullText = SystemDictUtil.getFullTextWithParent(this);
        return fullText;
    }
  
    public String getId() {
        return id;
    }
  
    public void setId(String id) {
        this.id = id;
    }
  
    public String getCategory() {
        return category;
    }
  
    public void setCategory(String category) {
        this.category = category;
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
  
    public String getLongText() {
        return longText;
    }
  
    public void setLongText(String longText) {
        this.longText = longText;
    }
  
    public String getEnabled() {
        return enabled;
    }
  
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
  
    public String getParentCode() {
        return parentCode;
    }
  
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
  
    public String getLeaf() {
        return leaf;
    }
  
    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }
  
    public Long getOrder() {
        return order;
    }
  
    public void setOrder(Long order) {
        this.order = order;
    }
}
