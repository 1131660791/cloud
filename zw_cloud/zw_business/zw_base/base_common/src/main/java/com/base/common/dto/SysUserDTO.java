package com.base.common.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

/**
 * 系统用户 DTO
 * <p>
 * 常用注解：
 * 代码    说明
 *
 * @author hzw
 * @Null 被注解的元素必须为null
 * @NotNull 被注解的元素必须不为null
 * @AssertTrue 被注解的元素必须为true
 * @AssertFalse 被注解的元素必须为false
 * @Min(value) 被注解的元素必须为数字，其值必须大于等于最小值
 * @Max(value) 被注解的元素必须为数字，其值必须小于等于最小值
 * @Size(max,min) 被注解的元素的大小必须在指定范围内
 * @Past 被注解的元素必须为过去的一个时间
 * @Future 被注解的元素必须为未来的一个时间
 * @Pattern 被注解的元素必须符合指定的正则表达式
 * <p>
 * 在方法入参中添加
 * @NotNull(message = "公共入参不能为空") CommonParam commonParam调用方法时即可验证
 * 或者利用
 * @Valid private CarrierInfoDTO carrierInfoDTO;
 * @Length( max = 200,
 * message = "年龄不能超过200"
 * )
 * @Range( min = 2L,
 * max = 4L,
 * message = "改址类型不合要求"
 * )
 * @NotNull( message = "号码为空"
 * )
 * @Within( values = {0L, 2L, 3L, 4L, 7L, 9L, 10L, 11L, 12L},
 * message = "类型(signType)不合法")
 */
@Data
public class SysUserDTO {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能空")
    private String userName;

    /**
     * 账号
     */
    @NotEmpty(message = "账号不能空")
    private String account;

    @Max(value = 100, message = "")
    private int ss;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能空")
    private String passWord;

}
