package com.base.feign.user;

import com.base.hystrix.user.UserFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * userFeign
 *
 * @author hzw
 */
@Component
@FeignClient(name = "base", value = "base", fallbackFactory = UserFeignHystrix.class)
public interface UserFeign {

    /**
     * 获取用户
     *
     * @return
     */
    @RequestMapping(value = "/base/getUser", method = RequestMethod.GET)
    String getUser();


    /**
     * 添加用户
     *
     * @return
     */
    @RequestMapping(value = "/base/addUser", method = RequestMethod.GET)
    void addUser();


}