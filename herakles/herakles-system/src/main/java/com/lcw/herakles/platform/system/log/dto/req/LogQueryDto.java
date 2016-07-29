package com.lcw.herakles.platform.system.log.dto.req;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.system.log.enumeration.ELogType;

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

    @Comment("日志类型")
    private ELogType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ELogType getType() {
        return type;
    }

    public void setType(ELogType type) {
        this.type = type;
    }

}
