package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DataTablesRequestOrderDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1243725115787446630L;

    private int column;
    private String dir;

    // public DataTablesRequestOrderDto() {
    // super();
    // }
    //
    // public DataTablesRequestOrderDto(int column, String dir) {
    // super();
    // this.column = column;
    // this.dir = dir;
    // }

}
