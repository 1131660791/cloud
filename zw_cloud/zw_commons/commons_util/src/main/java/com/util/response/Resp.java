package com.util.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回值
 */
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
    private String msg;

    /**
     * 响应码
     */
    @ApiModelProperty(value = "请求响应代码,0代表成功", example = "0")
    private int code;

    /**
     * 反馈数据
     */
    @ApiModelProperty(value = "请求返回的数据", example = "")
    private T result;

    @JsonIgnore
    private ResultCode resultCode;

    public Resp() {
        this.success = true;
        this.resultCode = ResultCode.DefaultResultCode.DEFAULT_SUCCESS;
        this.msg = "操作成功";
        this.code = resultCode.getCode();
    }

    public Resp(boolean success, ResultCode resultcode) {
        this.success = success;
        this.resultCode = resultcode;
        this.code = resultcode.getCode();
    }

    public Resp(boolean success, String msg, ResultCode resultCode) {
        this.success = success;
        this.msg = msg;
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
    }

    public Resp(T result) {
        this.success = true;
        this.resultCode = ResultCode.DefaultResultCode.DEFAULT_SUCCESS;
        this.code = resultCode.getCode();
        this.msg = "操作成功";
        this.result = result;
    }

    public static <E> Resp<E> ok(E result) {
        return new Resp<>(result);
    }

    public static Resp ok() {
        return new Resp();
    }

    public static Resp error() {
        return new Resp(false, ResultCode.DefaultResultCode.DEFAULT_FAILED);
    }

    public static Resp error(ResultCode resultcode) {
        return new Resp(false, resultcode);
    }

    public static Resp error(String msg) {
        return new Resp(false, msg, ResultCode.DefaultResultCode.DEFAULT_FAILED);
    }

    public static Resp error(ResultCode resultcode, String msg) {
        return new Resp(false, msg, resultcode);
    }
}
