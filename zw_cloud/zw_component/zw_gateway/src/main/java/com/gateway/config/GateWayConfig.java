package com.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    private Logger logger = LoggerFactory.getLogger(GateWayConfig.class);

    /**
     * 此过滤器可用于在请求主体被网关发送到下游之前对其进行修改。
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        logger.info("customRouteLocator ===log 记录==");
        return builder.routes()
                .route("base_route", r -> r.path("/base/**")
                        // lb://注册中心服务名
                        .uri("lb://base")
                ).route("provider_route", r -> r.path("/provider/{segment}")
                        .uri("http://localhost:8083")
                ).route("consumer_route", r -> r.path("/consumer/**")
                        .uri("lb://consumer")
                ).build();
    }

}
