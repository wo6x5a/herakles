package com.lcw.herakles.platform.common.dto;

import java.util.Date;

import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Comment(value = "基本可修改类继承dto")
public class BaseMaintainableDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = -1843259957447519196L;

    @Comment(value = "最后修改人编号")
    private String lastMntOpId;

    @Comment(value = "最后修改时间")
    private Date lastMntTs;

}
