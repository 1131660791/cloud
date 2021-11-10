package com.base.api.service.impl;

import com.base.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户服务层
 *
 * @author hzw
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public String getUser() {
        log.debug(" Base SpringSleuth getUser 用户");
        log.error(" Base error SpringSleuth getUser 用户");
        return "用户";
    }

}
