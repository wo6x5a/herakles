package com.lcw.herakles.platform.system.dict.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.common.enums.EFlagType;
import com.lcw.herakles.platform.common.enums.converter.EFlagTypeConverter;
import com.lcw.herakles.platform.system.dict.enums.EDictCategory;
import com.lcw.herakles.platform.system.dict.enums.converter.EDictCategoryConverter;

@Entity
@Table(name = "GL_DICT")
@EntityListeners(IdInjectionEntityListener.class)
public class DictPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;
  
    @Convert(converter = EDictCategoryConverter.class)
    @Column(name = "CATEGORY")
    private EDictCategory category;
  
    @Column(name = "DICT_CODE")
    private String dictCode;
  
    @Column(name = "DICT_TEXT")
    private String dictText;
    
    @Column(name = "DICT_TEXT_FULL")
    private String dictFullText;
  
    @Column(name = "PARENT_DICT_CODE")
    private String parentDictCode;
  
    @Convert(converter = EFlagTypeConverter.class)
    @Column(name = "ENABLE_FLG")
    private EFlagType enableFLag;
  
    @Convert(converter = EFlagTypeConverter.class)
    @Column(name = "LEAF_FLG")
    private EFlagType leafFlag;
  
    @Column(name = "DICT_ORD")
    private Long dictOrder;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public EDictCategory getCategory() {
      return category;
    }

    public void setCategory(EDictCategory category) {
      this.category = category;
    }

    public String getDictCode() {
      return dictCode;
    }

    public void setDictCode(String dictCode) {
      this.dictCode = dictCode;
    }

    public String getDictText() {
      return dictText;
    }

    public void setDictText(String dictText) {
      this.dictText = dictText;
    }

    public String getDictFullText() {
      return dictFullText;
    }

    public void setDictFullText(String dictFullText) {
      this.dictFullText = dictFullText;
    }

    public String getParentDictCode() {
      return parentDictCode;
    }

    public void setParentDictCode(String parentDictCode) {
      this.parentDictCode = parentDictCode;
    }

    public EFlagType getEnableFLag() {
      return enableFLag;
    }

    public void setEnableFLag(EFlagType enableFLag) {
      this.enableFLag = enableFLag;
    }

    public EFlagType getLeafFlag() {
      return leafFlag;
    }

    public void setLeafFlag(EFlagType leafFlag) {
      this.leafFlag = leafFlag;
    }

    public Long getDictOrder() {
      return dictOrder;
    }

    public void setDictOrder(Long dictOrder) {
      this.dictOrder = dictOrder;
    }
    
}
