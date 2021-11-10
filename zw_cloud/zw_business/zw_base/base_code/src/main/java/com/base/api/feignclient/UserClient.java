package com.base.api.feignclient;

import com.base.api.service.SysUserService;
import com.base.api.service.UserService;
import com.base.common.model.SysUser;
import com.base.feign.user.UserFeign;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * user 客户端
 *
 * @author hzw
 */
@RestController
@RequestMapping("base")
public class UserClient implements UserFeign {

    @Resource
    private UserService userService;

    @Resource
    private SysUserService sysUserService;

    @Override
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public String getUser() {
        return userService.getUser();
    }

    @Override
    public void addUser() {
        SysUser user = new SysUser();
        user.setUserName("Test");
        user.setAccount("account");
        sysUserService.addUser(user);
    }


}
