package com.base.api.controller;


import com.alibaba.nacos.common.util.UuidUtils;
import com.base.api.service.SysUserService;
import com.base.common.model.SysUser;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzw
 * @since 2021-01-15
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("addUser")
    public String addUser(){
        SysUser sysUser = new SysUser();
        sysUser.setAccount(UuidUtils.generateUuid());
        sysUser.setUserName(UuidUtils.generateUuid());
        sysUserService.addUser(sysUser);
        return "成功";
    }




}

