package com.lcw.herakles.platform.log.dto.req;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.log.enums.EEntityType;

/**
 * 日志查询dto
 * 
 * @author chenwulou
 *
 */
public class LogQueryDto extends DataTablesRequestDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment("编号")
    private String id;

    @Comment("操作实体类型")
    private EEntityType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EEntityType getType() {
        return type;
    }

    public void setType(EEntityType type) {
        this.type = type;
    }

}
