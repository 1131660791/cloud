package com.zw.provider.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zw.provider.bean.Product;
import com.zw.provider.mapper.ProductMapper;
import com.zw.provider.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hzw
 * @since 2019-10-24
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    public IPage<Product> findProduct(Page page) {
        IPage<Product> products = productMapper.findProductPage(page,new Product());
        return products;
    }
}
