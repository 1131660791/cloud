package com.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.util.constant.AuthProvider;
import com.util.jwt.JwtUtil;
import com.util.response.Resp;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * token 验证
 *
 * @author hzw
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthTokenFilter implements GlobalFilter, Ordered {

    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isSkip(path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken)) {
            return unAuth(resp, "缺失令牌,鉴权失败");
        }
        String token = JwtUtil.getToken(headerToken);
        Claims claims = JwtUtil.parseJWT(token);
        if (claims == null) {
            return unAuth(resp, "请求未授权");
        }
        return chain.filter(exchange);
    }

    /**
     * 放过验证权限
     *
     * @param path
     * @return
     */
    private boolean isSkip(String path) {
        List<String> defaultSkipUrl = AuthProvider.getDefaultSkipUrl();
        System.out.println(defaultSkipUrl);
        boolean isSkip = defaultSkipUrl.stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
        return isSkip;
    }

    /**
     * 验证返回
     *
     * @param resp
     * @param msg
     * @return
     */
    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            result = objectMapper.writeValueAsString(Resp.error(msg));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}