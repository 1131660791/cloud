package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 微服务模式，在Spring cloud gateway网关处进行用户认证与授权有两种方式：
 * 1:在Spring Cloud Gateway服务这里添加数据库访问，直接检测登陆信息是否正确，如果正确，再给此用户授权。
 * 2:在网关后面专门的认证服务进行登陆信息认证，如果登陆成功，在返回的Header中添加用户认证与授权需要的信息，然后在网关处理再完成认证与授权
 *
 * @author hzw
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

}