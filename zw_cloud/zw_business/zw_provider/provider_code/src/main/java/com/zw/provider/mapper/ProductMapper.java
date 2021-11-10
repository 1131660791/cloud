package com.zw.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zw.provider.bean.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzw
 * @since 2019-10-24
 */
@Repository("productMapper")
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> findProductPage(Page page, Product product);
}
