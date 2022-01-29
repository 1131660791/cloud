package com.util.system.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

    @Valid
    @NotEmpty(message = "请求参数对象不能为空")
    @ApiModelProperty(value = "请求参数对象", example = "")
    protected T data;

}