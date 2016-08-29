package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Value object for jQuery DataTables JSON response.
 * 
 * @author chenwulou
 * 
 * @param <T>
 */
@SuppressWarnings("serial")
public class DataTablesResponseDto<T> implements Serializable {

    @JsonProperty(value = "iTotalRecords")
    private long totalRecords;

    @JsonProperty(value = "iTotalDisplayRecords")
    private long totalDisplayRecords;

    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "sColumns")
    private String columns;

    @JsonProperty(value = "aaData")
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
