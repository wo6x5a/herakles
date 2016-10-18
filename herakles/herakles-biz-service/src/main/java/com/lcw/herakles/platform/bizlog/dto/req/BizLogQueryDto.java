package com.lcw.herakles.platform.bizlog.dto.req;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志查询dto
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment("日志查询Dto")
public class BizLogQueryDto extends DataTablesRequestDto {

    /**
     * 
     */
    private static final long serialVersionUID = 5021339458232268157L;

    @Comment("编号")
    private String id;

    @Comment("操作实体")
    private String entity;

    @Comment("操作类型")
    private EOptType type;

}
