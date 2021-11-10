package com.util.response;

import lombok.*;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

//    @Valid
//    @NotNull(message = "请求参数对象不能为空")
    @ApiModelProperty(value = "请求参数对象", example = "")
    protected T data;

}