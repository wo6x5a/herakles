package com.lcw.herakles.platform.common.dto;

import java.io.Serializable;

import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@Comment(value = "模糊查询select值返回dto")
public class FuzzySelectRespDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7985714345219136208L;

    @Comment(value = "select value 对应 key(一般为po的id)")
    private String key;

    @Comment(value = "select 显示字段")
    private String value;

}
