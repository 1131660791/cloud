package com.util.system.euums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 所有返回状态定义
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    DEFAULT_SUCCESS(200, "成功"),
    /**
     * 失败
     */
    DEFAULT_FAILED(50_001, "失败", "%s"),

    // 全局错误
    SYSTEM_ERROR(99_010, "系统异常"),
    BUSINESS_ERROR(99_020, "业务异常", "(%s)业务发生异常"),
    UNKNOWN_ERROR(99_030, "未知错误"),

    // 用户认证
    UNAUTHORIZED(98_010, "用户未授权", "用户[%s]未授权"),
    LOGIN_FAILED(98_020, "登录失败", "%s"),
    TOKEN_EXPIRED(98_030, "授权令牌已失效", "授权令牌[%s]已失效"),
    PERMISSION_DENIED(98_040, "无权查询数据", "[%s]无权查询数据"),
    USER_NOT_REGISTER(98_050, "用户未注册", "用户[%s]未注册"),

    // 参数校验
    PARAMETER_VALIDATED(97_000, "参数校验"),
    PARAMETER_REQUIRED(97_010, "参数必传", "参数[%s]不能不空"),
    PARAMETER_FORMAT_ERROR(97_020, "参数格式错误", "参数[%s]格式不正确"),
    PARAMETER_RANGE_ERROR(97_030, "数据范围错误"),
    PARAMETER_VALUE_ERROR(97_040, "参数值错误", "(%s)"),
    REPEAT_SUBMIT(97_050, "数据重复提交", "(%s)"),

    // 增删改查
    CREATE_FAILED(96_010, "增加失败", "[%s]失败"),
    UPDATE_FAILED(96_020, "修改失败", "修改[%s]失败"),
    DELETE_FAILED(96_030, "删除失败", "删除[%s]失败"),
    DISABLE_FAILED(96_040, "禁用失败", "禁用[%s]失败"),
    ENABLE_FAILED(96_050, "启用失败", "启用[%s]失败"),
    OFFLINE_FAILED(96_060, "下线失败", "下线[%s]失败"),
    ONLINE_FAILED(96_070, "上线失败", "上线[%s]失败"),
    AUDIT_FAILED(96_080, "审核失败", "审核[%s]失败"),
    DATA_EXISTED(96_090, "数据已经存在", "[%s]已经存在"),
    DATA_NOT_FOUND(95_100, "数据未找到", "[%s]数据未找到"),

    ;

    private final static Map<Integer, ResultCode> BY_CODE_MAP = Arrays.stream(ResultCode.values())
            .collect(Collectors.toMap(ResultCode::getCode, code -> code));

    private final int code;

    private final String desc;

    private final String template;


    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
        this.template = "";
    }

    ResultCode(int code, String desc, String template) {
        this.code = code;
        this.desc = desc;
        this.template = template;
    }

    /**
     * @param code 代码
     * @return 转换出来的状态码
     */
    public static ResultCode parse(Integer code) {
        return BY_CODE_MAP.getOrDefault(code, ResultCode.SYSTEM_ERROR);
    }
}
