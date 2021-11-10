package com.base.hystrix.user;

import com.base.feign.user.UserFeign;
import feign.hystrix.FallbackFactory;


public class UserFeignHystrix  implements FallbackFactory<UserFeign> {

    @Override
    public UserFeign create(Throwable throwable) {
        return null;
    }

}