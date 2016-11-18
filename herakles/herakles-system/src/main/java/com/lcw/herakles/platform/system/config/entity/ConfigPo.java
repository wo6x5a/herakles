package com.lcw.herakles.platform.system.config.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.converter.ECfgTypeEnumConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Entity
@Table(name = "gl_config")
@Comment(value = "参数表")
public class ConfigPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cfg_key")
    @ColumnMeta(length = "32", nullable = false, comment = "参数编号")
    private String key;

    @Column(name = "cfg_value")
    @ColumnMeta(length = "40", nullable = false, comment = "参数值")
    private String value;

    @Column(name = "cfg_type")
    @Convert(converter = ECfgTypeEnumConverter.class)
    @ColumnMeta(length = "1", nullable = false, comment = "参数类型")
    private ECfgType type;

    @Column(name = "descr")
    @ColumnMeta(length = "100", nullable = false, comment = "参数备注")
    private String descr;

}
