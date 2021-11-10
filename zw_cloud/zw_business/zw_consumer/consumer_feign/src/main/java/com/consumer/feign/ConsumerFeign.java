package com.consumer.feign;

import com.consumer.hystrix.ConsumerHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * feign
 *
 * @author hzw
 */
@Component
@FeignClient(name = "consumer", value = "consumer", fallbackFactory = ConsumerHystrix.class)
public interface ConsumerFeign {

    /**
     * @return
     */
    @RequestMapping(value = "/consumer/consumerS", method = RequestMethod.GET)
    String consumerS();

}