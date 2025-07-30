package com.example.chatgpt_tool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 所有路径都允许跨域
                .allowedOriginPatterns("*") // 允许所有前端地址（推荐用 allowedOriginPatterns 而不是 allowedOrigins）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)     // 如果你使用了 Cookie 或 token
                .maxAge(3600);              // 预检请求缓存 1 小时
    }
}

