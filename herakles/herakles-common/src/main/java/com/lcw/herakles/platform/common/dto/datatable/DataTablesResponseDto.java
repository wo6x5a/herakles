package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Value object for jQuery DataTables JSON response.
 * 
 * @author chenwulou
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
@ApiModel(value = "分页返回DTO", description = "分页返回数据封装")
public class DataTablesResponseDto<T> implements Serializable {

    @JsonProperty(value = "iTotalRecords")
	@ApiModelProperty(value = "总条数")
    private long totalRecords;

    @JsonProperty(value = "iTotalDisplayRecords")
	@ApiModelProperty(value = "totalDisplayRecords")
    private long totalDisplayRecords;

	@ApiModelProperty(value = "echo")
    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "sColumns")
	@ApiModelProperty(value = "columns")
    private String columns;

    @JsonProperty(value = "aaData")
	@ApiModelProperty(value = "数据")
    private List<T> data = new ArrayList<T>();

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public void setTotalDisplayRecords(long totalDisplayRecords) {
        this.totalDisplayRecords = totalDisplayRecords;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
