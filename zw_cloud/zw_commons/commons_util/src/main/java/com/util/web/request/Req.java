package com.util.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Req<T> {

    @Valid
    @NotNull(message = "{data.not.null}")
    @ApiModelProperty(value = "请求参数对象", example = "")
    protected T data;

}
