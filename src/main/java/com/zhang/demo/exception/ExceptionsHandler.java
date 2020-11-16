package com.zhang.demo.exception;

import com.zhang.demo.entity.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常接管
 *
 * @author Li Jinhui
 * @update 2019/1/7 09:22
 * @since 2018/12/6 10:00
 */
@RestControllerAdvice
public class ExceptionsHandler {

    @Autowired
    private HttpServletRequest request;

    /**
     * 注意：
     * 启用全局异常接管后，没有在此处定义拦截的异常都会默认返回500错误。
     * 若需要自定义拦截的异常，请在此处定义拦截。
     * 若需要输出异常的日志日志，请使用logger输出。
     */
    private final Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    /**
     * 基本异常
     */
    @ExceptionHandler(Exception.class)
    public ResultObject exception(Exception e) {
        return new ResultObject().error("Error", 500);
    }

    /**
     * 请求路径无法找到异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultObject notFoundException() {
        return new ResultObject().error("Not found", 404);
    }

    /**
     * 请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultObject httpRequestMethodNotSupportedException() {
        return new ResultObject().error("Method not allowed", 405);
    }

    /**
     * 请求参数异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, MissingServletRequestPartException.class, BindException.class})
    public ResultObject parameterException() {
        return new ResultObject().error("Parameter error", 403);
    }

    /**
     * 上传文件过大异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultObject maxUploadSizeExceededException() {
        return new ResultObject().error("File is too large", 403);
    }

    /**
     * 服务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResultObject serviceException(ServiceException e) {
        return new ResultObject().error(e.getCode(), e.getMessage(), e.getData());
    }

    /**
     * 自定义异常
     * 配合com.zhang.demo.exception.UserDefinedException 和 ResponseCode 使用
     * @param exception
     * @return
     */
    @ExceptionHandler(UserDefinedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultObject sendErrorResponse_UserDefined(UserDefinedException  exception){
        return new ResultObject().error(exception.getException().getCode(),
                exception.getException().getMsg(), null);
    }

}