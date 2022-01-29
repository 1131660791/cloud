package com.util.system.web.request;

import com.util.system.euums.SortTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;

/**
 * 分页请求
 *
 * @param <T>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageReq<T> extends Req<T> {

    /**
     * 页码
     */
    @ApiModelProperty(value = "当前页码", example = "1")
    @Min(value = 1, message = "{page.number.min}")
    private int pageNumber;

    /**
     * 分页大小
     */
    @ApiModelProperty(value = "分页大小", example = "10")
//  @Range(min = 1, max = 1000, message = "{page.size.range}")
    private int pageSize;

    /**
     * 排序列的列名称
     */
    @ApiModelProperty(value = "排序列的列名", example = "gmtCreate")
    private String sortName;

    /**
     * 排序方向：ASC | DESC
     */
    @ApiModelProperty(value = "排序方向", example = "DESC")
    private SortTypeEnum sortType;
}
