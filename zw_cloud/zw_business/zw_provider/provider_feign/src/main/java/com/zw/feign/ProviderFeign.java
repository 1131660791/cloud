package com.zw.feign;

import com.zw.hystrix.ProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "provider", fallbackFactory = ProviderFallback.class)
public interface ProviderFeign {

    @RequestMapping(value = "/provider/getProvider", method = RequestMethod.GET)
    String getProvider();

}
