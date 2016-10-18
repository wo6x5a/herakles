package com.lcw.herakles.platform.common.dto;

import java.io.Serializable;
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
@Comment(value = "基本继承dto")
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Comment(value = "创建人编号")
    private String createOpId;

    @Comment(value = "创建时时间")
    private Date createTs;


}
