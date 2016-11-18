package com.lcw.herakles.platform.system.config.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.EConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * 参数信息
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment("参数信息Dto")
public class ConfigDto extends BaseMaintainableDto {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment("参数编号")
    private EConfig key;

    @Comment("参数值")
    private String value;

    @Comment("参数类型")
    private ECfgType cfgType;

    @Comment("参数备注")
    private String descr;

}
