package com.lcw.herakles.platform.system.dict.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.common.enums.EFlagType;
import com.lcw.herakles.platform.common.enums.converter.EFlagTypeConverter;
import com.lcw.herakles.platform.system.dict.enums.EDictCategory;
import com.lcw.herakles.platform.system.dict.enums.converter.EDictCategoryConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment(value = "数据字典")
@Entity
@Table(name = "gl_dict")
@EntityListeners(IdInjectionEntityListener.class)
public class DictPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @ColumnMeta(length = "32", nullable = false, comment = "id")
    private String id;
  
    @Convert(converter = EDictCategoryConverter.class)
    @Column(name = "category")
    @ColumnMeta(length = "20", nullable = false, comment = "类型")
    private EDictCategory category;

    @Column(name = "dict_code")
    @ColumnMeta(length = "100", nullable = false, comment = "字典编号")
    private String dictCode;

    @Column(name = "dict_text")
    @ColumnMeta(length = "100", nullable = false, comment = "字典内容")
    private String dictText;

    @Column(name = "dict_text_full")
    @ColumnMeta(length = "200", nullable = false, comment = "字典内容详情")
    private String dictFullText;

    @Comment(value = "父节点编号")
    @Column(name = "parent_dict_code")
    @ColumnMeta(length = "100", nullable = false, comment = "父节点编号")
    private String parentDictCode;

    @Comment(value = "是否可用")
    @Convert(converter = EFlagTypeConverter.class)
    @Column(name = "enable_flg")
    @ColumnMeta(length = "1", nullable = false, comment = "是否可用,1:是,2:否")
    private EFlagType enableFLag;

    @Convert(converter = EFlagTypeConverter.class)
    @Column(name = "leaf_flg")
    @ColumnMeta(length = "1", nullable = false, comment = "是否子节点,1:是,2:否")
    private EFlagType leafFlag;

    @Comment(value = "")
    @Column(name = "dict_ord")
    @ColumnMeta(length = "11", nullable = false, comment = "字典顺序")
    private Long dictOrder;
    
}
