
package com.lcw.herakles.platform.bizlog.dto;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment("系统业务日志dto")
public class BizLogDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = 7200782433106609764L;

    @Comment("编号")
    private String id;

    @Comment("操作实体")
    private String entity;

    @Comment("实体编号")
    private String entityId;

    @Comment("操作类型")
    private EOptType optType;

    @Comment("具体操作")
    private String operate;

    @Comment("操作IP地址")
    private String optIp;

    @Comment("新数据")
    private String newVaule;

    @Comment("旧数据")
    private String oldVaule;

    @Comment("备注")
    private String descr;

}
