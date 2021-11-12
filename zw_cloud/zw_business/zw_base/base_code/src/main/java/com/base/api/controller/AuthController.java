package com.base.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.api.service.SysUserService;
import com.base.common.model.SysUser;
import com.util.encryption.rsa.RSAUtil;
import com.util.jwt.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigInteger;

@RestController
@RequestMapping("/auth")
@Api(value = "AuthController", tags = {"认证"})
public class AuthController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login")
    @ApiOperation(value = "登录", notes = "")
    public String login(String account) {
        QueryWrapper<SysUser> userQuery = new QueryWrapper();
        userQuery.eq("account", account);
        SysUser sysUser = sysUserService.getOne(userQuery);
        if (sysUser == null) {
            return "未找到该用户";
        }
        BigInteger publicKey = RSAUtil.getPublicKey().getModulus();
        return "登录成功: " + JwtUtil.createJWT(System.currentTimeMillis(), sysUser.getId().toString(), sysUser.getUserName(), sysUser.getPassWord(), publicKey);
    }
}
