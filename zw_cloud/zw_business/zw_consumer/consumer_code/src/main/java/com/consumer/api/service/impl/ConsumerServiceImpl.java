package com.consumer.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.feign.user.UserFeign;
import com.consumer.api.mapper.ConsumerMapper;
import com.consumer.api.pojo.Consumer;
import com.consumer.api.service.ConsumerService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzw
 * @since 2020-05-20
 */
@Service("consumerService")
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {

    private Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private UserFeign userFeign;

    @Override
    public String getss() {
        userFeign.addUser();
        logger.info("链路 consumerService - getss");
        return userFeign.getUser();
    }

}
