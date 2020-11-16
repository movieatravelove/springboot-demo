package com.zhang.demo.exception;

import com.zhang.demo.entity.ResponseCode;

/**
 * @Author: zhang
 * @Date: 2020/11/9 11:25
 * @Description: 自定义异常
 * 配合 ExceptionsHandler 和 ResponseCode 使用
 * throw new UserDefinedException(ResponseCode.NEED_LOGIN);
 * @Version: 1.0
 */
public class UserDefinedException extends RuntimeException {

    private ResponseCode responseCode;

    public UserDefinedException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseCode getException() {
        return responseCode;
    }

    public void setException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
