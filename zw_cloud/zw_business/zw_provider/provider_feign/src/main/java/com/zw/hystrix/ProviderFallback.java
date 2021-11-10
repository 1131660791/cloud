package com.zw.hystrix;

import com.zw.feign.ProviderFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProviderFallback implements FallbackFactory<ProviderFeign> {


    
    @Override
    public ProviderFeign create(Throwable cause) {
        return null;
    }
}
