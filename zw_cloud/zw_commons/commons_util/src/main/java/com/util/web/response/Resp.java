package com.util.web.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.util.exception.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Resp<T> {

    /**
     * 是否成功
     */
    @ApiModelProperty(value = "请求是否成功标志,成功:true,失败:false", example = "true")
    private boolean success;

    /**
     * 反馈信息
     */
    @ApiModelProperty(value = "反馈信息", example = "操作成功！")
    private String message;

    /**
     * 响应码
     */
    @ApiModelProperty(value = "请求响应代码,0代表成功", example = "0")
    private int code;

    /**
     * 反馈数据
     */
    @ApiModelProperty(value = "请求返回的数据", example = "")
    private T data;

    @JsonIgnore
    private ResultCode resultCode;

    public Resp() {
        this.success = true;
        this.resultCode = ResultCode.DefaultResultCode.DEFAULT_SUCCESS;
        this.code = resultCode.getCode();
    }

    public Resp(boolean success, ResultCode resultCode) {
        this.success = success;
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }

    public Resp(boolean success, String message, ResultCode resultCode) {
        this.success = success;
        this.message = message;
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }

    public Resp(T data) {
        this();
        this.data = data;
    }

    public static <E> Resp<E> ok(E data) {
        return new Resp<>(data);
    }

    public static Resp ok() {
        return new Resp();
    }

    public static Resp error() {
        return new Resp(false, ResultCode.DefaultResultCode.DEFAULT_FAILED);
    }

    public static Resp error(ResultCode resultCode) {
        return new Resp(false, resultCode);
    }

    public static Resp error(String message) {
        return new Resp(false, message,  ResultCode.DefaultResultCode.DEFAULT_FAILED);
    }

    public static Resp error(ResultCode resultCode, String message) {
        return new Resp(false, message, resultCode);
    }
}
