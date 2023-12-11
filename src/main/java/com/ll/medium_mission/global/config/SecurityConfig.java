package com.ll.medium_mission.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .cors(cors -> cors
                        .configure(http)
                )
                .headers(
                        headers -> headers.frameOptions(FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(
                                mvc.pattern("/member/join"),
                                mvc.pattern("/member/login"),
                                mvc.pattern("/post"),
                                mvc.pattern("/post/{postId:[0-9]+}"),
                                mvc.pattern("/member/join")
                        )
                );


        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}
