package com.consumer.api.controller;

import com.consumer.api.pojo.Consumer;
import com.consumer.api.service.ConsumerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzw
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private ConsumerService consumerService;

    @RequestMapping(value = "/getConsumer")
    public List<Consumer> getConsumer() {
        return consumerService.list();
    }

    @RequestMapping(value = "/ss")
    public String ss() {
        return consumerService.getss();
    }

}

