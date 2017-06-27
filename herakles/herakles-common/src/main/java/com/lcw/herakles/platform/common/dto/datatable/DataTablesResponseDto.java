package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

/**
 * Value object for jQuery DataTables JSON response.
 * 
 * @author chenwulou
 * 
 * @param <T>
 */
@Getter
@Setter
@Comment(value = "datatable分页返回dto")
public class DataTablesResponseDto<T> implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment(value = "总记录数")
    @JsonProperty(value = "iTotalRecords")
    private long totalRecords;

    @Comment(value = "总显示条数")
    @JsonProperty(value = "iTotalDisplayRecords")
    private long totalDisplayRecords;

    @Comment(value = "echo")
    @JsonProperty(value = "sEcho")
    private String echo;

    @Comment(value = "列")
    @JsonProperty(value = "sColumns")
    private String columns;

    @Comment(value = "数据")
    @JsonProperty(value = "aaData")
    private List<T> data = Lists.newArrayList();

}
