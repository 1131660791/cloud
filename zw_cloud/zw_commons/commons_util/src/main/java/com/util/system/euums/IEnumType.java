package com.util.system.euums;

/**
 * 枚举父类
 *
 * @author hzw
 */
public interface IEnumType {

    /**
     * 数据库中定义的 数字 状态码
     *
     * @return 数字状态
     */
    int getCode();

    /**
     * 简单描述
     *
     * @return 简单描述
     */
    String getDesc();
}
