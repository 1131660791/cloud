package com.consumer.hystrix;

import com.consumer.feign.ConsumerFeign;
import feign.hystrix.FallbackFactory;

public class ConsumerHystrix implements FallbackFactory<ConsumerFeign> {


    @Override
    public ConsumerFeign create(Throwable throwable) {
        return null;
    }
}
