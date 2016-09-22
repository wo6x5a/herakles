package com.lcw.herakles.platform.common.dto;

import java.io.Serializable;

/**
 * 模糊查询select值返回dto
 * 
 * List<FuzzSelectRespDto> resp = Lists.newArrayList();
 * <p>
 * resp = xxService.getxxx(keywords);
 * <p>
 * return resp;
 * 
 * @author chenwulou
 *
 */
public class FuzzySelectRespDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7985714345219136208L;

    /** select value 对应 key(一般为po的id) **/
    private String key;

    /** select 显示字段 **/
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
