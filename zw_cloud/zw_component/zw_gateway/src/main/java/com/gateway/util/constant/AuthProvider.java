package com.gateway.util.constant;

import com.util.constant.auth.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证
 * @author hzw
 */
public class AuthProvider {

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";
    public static String AUTH_KEY = TokenConstant.HEADER;
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/auth/**");
    }

    /**
     * 默认无需鉴权的API
     */
    public static List<String> getDefaultSkipUrl() {
        return defaultSkipUrl;
    }

}
