package com.ll.medium_mission.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/md/**")
                .allowedOrigins("http://localhost:5173")
                .allowedOrigins("https://medium.bbgk.me")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
