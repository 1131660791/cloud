package com.util.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageReq<T> extends Req<T> {

    /**
     * 页码
     */
    @ApiModelProperty(value = "当前页码", example = "1")
    private int curPage = 1;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", example = "10")
    private int limit = 10;

    /**
     * 排序列的列名称
     */
    @ApiModelProperty(value = "排序列的列名", example = "gmtCreate")
    private String sortName;

    /**
     * 排序方向：ASC | DESC
     */
    @ApiModelProperty(value = "排序方向", example = "DESC")
    private String sortType = "desc";
}
