package com.lcw.herakles.platform.common.dto.datatable;

import java.io.Serializable;

/**
 * @author chenwulou
 *
 */
@SuppressWarnings("serial")
public class DataTablesRequestOrderDto implements Serializable {

    private int column;
    private String dir;

	public DataTablesRequestOrderDto() {
		super();
	}

	public DataTablesRequestOrderDto(int column, String dir) {
		super();
		this.column = column;
		this.dir = dir;
	}

	public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

}
