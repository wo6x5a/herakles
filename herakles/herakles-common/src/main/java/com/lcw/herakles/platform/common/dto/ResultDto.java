package com.lcw.herakles.platform.common.dto;

import java.io.Serializable;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.constant.ResultCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class Name: ResultDto
 * 
 * @author chenwulou
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Comment(value = "结果返回dto")
public class ResultDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Comment(value = "返回码")
    private String code;
    
    @Comment(value = "返回消息")
    private String message;
    
    @Comment(value = "返回数据")
    private Object data;

    /**
     * @NoArgsConstructor 
     * 
     * ResultDto Constructor
     */
    // public ResultDto() {
    //
    // }

    /**
     * @AllArgsConstructor(access = AccessLevel.PUBLIC)
     * 
     * ResultDto Constructor
     * @param code
     * @param message
     * @param data
     */
    // public ResultDto(String code, String message, Object data) {
    // this.code = code;
    // this.message = message;
    // this.data = data;
    // }

    /**
     * Description: whether this is non business error
     *
     * @return
     */
    public boolean isNonBizError() {
        return ResultCode.SESSION_TIME_OUT.equals(code) || ResultCode.COMMON_ERROR.equals(code);
    }
}
