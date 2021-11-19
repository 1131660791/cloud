package com.util.exception;

/**
 * 返回值
 * @author hzw
 */
public interface ResultCode {

    String name();

    int getCode();

    enum DefaultResultCode implements ResultCode {

        /**
         * 成功
         */
        DEFAULT_SUCCESS(0),

        /**
         * 失败
         */
        DEFAULT_FAILED(1),

        /**
         * 登录已过期
         */
        EXPIRED_LOGIN_EXCEPTION(2),

        /**
         * 数据库异常
         */
        DAO_EXCEPTION(3),

        /**
         * 用户未登录
         */
        NOT_LOGIN_EXCEPTION(4),

        /**
         * 用户不存在
         */
        USER_NOT_FOUND_EXCEPTION(5),

        /**
         * 没有权限
         */
        NO_PERMISSIONS_EXCEPTION(6),

        /**
         * 参数校验异常
         */
        PARAMETER_EXCEPTION(7),

        /**
         * 资源不存在
         */
        RESOURCE_NOT_FOUND_EXCEPTION(8),

        /**
         * 调用外部系统接口异常
         */
        EXTERNAL_SYSTEM_EXCEPTION(9),

        /**
         * 参数解析异常
         */
        PARSE_EXCEPTION(10),
        /**
         * 资源已经存在
         */
        RESOURCE_ALREADY_EXIST_EXCEPTION(11),

        /**
         * 文件上传异常
         */
        FILE_UPLOAD_EXCEPTION(12),

        /**
         * 请求方法的http方法不支持
         */
        HTTP_METHOD_NOT_SUPPORTED_EXCEPTION(13),

        /**
         * bean创建或拷贝异常
         */
        BEAN_CREATION_EXCEPTION(14),

        /**
         * 执行不允许的操作时发生的异常
         */
        OPERATION_NOT_ALLOWED_EXCEPTION(15),

        /**
         * 用户无访问权限异常
         */
        USER_ACCESS_EXCEPTION(16),
        /**
         * code码重复异常
         */
        RESOURCE_UNIQUE_CODE_EXCEPTION(16),

        /**
         * 未知错误
         */
        UNKNOWN_EXCEPTION(99);

        private int code;

        DefaultResultCode(int code) {
            this.code = code;
        }


        @Override
        public int getCode() {
            return code;
        }
    }
}
