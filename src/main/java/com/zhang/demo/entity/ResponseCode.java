package com.zhang.demo.entity;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:12
 * @Description: 返回状态码
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(500, "ERROR"),
    NEED_LOGIN(100, "需要登录"),
    ERROR_LOGIN(501, "你输入的账号和密码有误!!"),
    ERROR_CODE_LOGIN(502, "你输入的验证有误!!");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}