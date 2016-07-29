package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Value object for jQuery DataTables JSON request. Below is an example of the request JSON:
 * 
 * { "sEcho":1, "iColumns":7, "sColumns":"", "iDisplayStart":0, "iDisplayLength":10, "mDataProp":[0,1,2,3,4,5,6],
 * "sSearch":"", "bRegex":false, "asSearch":["","","","","","",""],
 * "abRegex":[false,false,false,false,false,false,false], "abSearchable":[true,true,true,true,true,true,true],
 * "iSortingCols":1, "aiSortCol":[0], "asSortDir":["asc"], "abSortable":[true,true,true,true,true,true,true] }
 * 
 * @author chenwulou
 */
@SuppressWarnings("serial")
public class DataTablesRequestDto implements Serializable {

    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "iColumns")
    private int numColumns;

    @JsonProperty(value = "sColumns")
    private String columns;

    @JsonProperty(value = "iDisplayStart")
    private int displayStart;

    @JsonProperty(value = "iDisplayLength")
    private int displayLength;

    // has to be revisited for Object type dataProps.
    @JsonProperty(value = "amDataProp")
    private List<String> dataProps;

    @JsonProperty(value = "sSearch")
    private String searchQuery;

    @JsonProperty(value = "asSearch")
    private List<String> columnSearches;

    @JsonProperty(value = "bRegex")
    private boolean hasRegex;

    @JsonProperty(value = "abRegex")
    private List<Boolean> regexColumns;

    @JsonProperty(value = "abSearchable")
    private List<Boolean> searchColumns;

    @JsonProperty(value = "iSortingCols")
    private int sortingCols;

    @JsonProperty(value = "aiSortCol")
    private List<Integer> sortedColumns;

    @JsonProperty(value = "asSortDir")
    private List<String> sortDirections;

    @JsonProperty(value = "abSortable")
    private List<Boolean> sortableColumns;

    @JsonProperty(value = "projectId")
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public int getDisplayStart() {
        return displayStart;
    }

    public void setDisplayStart(int displayStart) {
        this.displayStart = displayStart;
    }

    public int getDisplayLength() {
        return displayLength;
    }

    public void setDisplayLength(int displayLength) {
        this.displayLength = displayLength;
    }

    public List<String> getDataProps() {
        return dataProps;
    }

    public void setDataProp(List<String> dataProps) {
        this.dataProps = dataProps;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getColumnSearches() {
        return columnSearches;
    }

    public void setColumnSearches(List<String> columnSearches) {
        this.columnSearches = columnSearches;
    }

    public boolean isHasRegex() {
        return hasRegex;
    }

    public void setHasRegex(boolean hasRegex) {
        this.hasRegex = hasRegex;
    }

    public List<Boolean> getRegexColumns() {
        return regexColumns;
    }

    public void setRegexColumns(List<Boolean> regexColumns) {
        this.regexColumns = regexColumns;
    }

    public List<Boolean> getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(List<Boolean> searchColumns) {
        this.searchColumns = searchColumns;
    }

    public int getSortingCols() {
        return sortingCols;
    }

    public void setSortingCols(int sortingCols) {
        this.sortingCols = sortingCols;
    }

    public List<Integer> getSortedColumns() {
        return sortedColumns;
    }

    public void setSortedColumns(List<Integer> sortedColumns) {
        this.sortedColumns = sortedColumns;
    }

    public List<String> getSortDirections() {
        return sortDirections;
    }

    public void setSortDirections(List<String> sortDirections) {
        this.sortDirections = sortDirections;
    }

    public List<Boolean> getSortableColumns() {
        return sortableColumns;
    }

    public void setSortableColumns(List<Boolean> sortableColumns) {
        this.sortableColumns = sortableColumns;
    }
}
