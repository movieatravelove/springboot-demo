package com.zhang.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 返回结果实体类
 *
 */
@Getter
public class ResultObject {

    @ApiModelProperty(value = "返回状态码，0（成功）")
    private int code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    private ResultObject setResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public ResultObject success() {
        return setResult(0, "success", null);
    }

    public ResultObject success(Object data) {
        return setResult(0, "success", data);
    }

    public ResultObject error(String message, Object data) {
        return setResult(500, message, data);
    }

    public ResultObject error(int code, String message, Object data) {
        return setResult(code, message, data);
    }

    public ResultObject error(int code, String message) {
        return setResult(code, message, null);
    }
    
    public ResultObject error(String message) {
        return setResult(500, message, null);
    }
}