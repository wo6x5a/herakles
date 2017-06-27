package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


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
@Getter
@Setter
public class DataTablesRequestDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
}
