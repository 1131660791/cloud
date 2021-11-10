package com.base.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.api.mapper.SysUserMapper;
import com.base.api.service.SysUserService;
import com.base.common.model.SysUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzw
 * @since 2021-01-15
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public void addUser(SysUser user) {
        this.save(user);
    }

}
