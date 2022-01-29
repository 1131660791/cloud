package com.util.system.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 分页 bean
 *
 * @param <T>
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页码
     */
    private int pageNumber;

    /**
     * 分页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 是否有上一页
     */
    private boolean hasPre;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    public PageBean(List<T> data, Long total, int pageNumber, int pageSize) {
        this.data = data;
        this.total = total;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

        this.pages = (this.total + this.pageSize - 1) / this.pageSize;
        this.hasPre = this.pageNumber > 0;
        this.hasNext = this.pageNumber < this.pages;

    }
}
