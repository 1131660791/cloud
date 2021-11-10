package com.consumer.api.feignclient;

import com.consumer.api.service.ConsumerService;
import com.consumer.feign.ConsumerFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消费者客户端 feign
 *
 * @author hzw
 */
@RestController
@RequestMapping("consumer")
public class ConsumerClient implements ConsumerFeign {

    @Resource
    private ConsumerService consumerService;

    @Override
    @RequestMapping(value = "/consumerS", method = RequestMethod.GET)
    public String consumerS() {
        return consumerService.getss();
    }
}
