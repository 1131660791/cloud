package com.base.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.common.model.SysUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hzw
 * @since 2021-01-15
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 添加用户信息
     *
     * @param user
     */
    void addUser(SysUser user);

}
