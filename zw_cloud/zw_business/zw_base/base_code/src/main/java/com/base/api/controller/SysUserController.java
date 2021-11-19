package com.base.api.controller;


import com.alibaba.nacos.common.util.UuidUtils;
import com.base.api.service.SysUserService;
import com.base.common.dto.SysUserDTO;
import com.base.common.model.SysUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzw
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("addUser")
    public String addUser() {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(UuidUtils.generateUuid());
        sysUser.setUserName(UuidUtils.generateUuid());
        sysUserService.addUser(sysUser);
        return "成功";
    }


    @RequestMapping("userDto")
    public String userDto(@Valid SysUserDTO sysUserDTO) {
        System.out.println(sysUserDTO);
        return "成功";
    }

}

