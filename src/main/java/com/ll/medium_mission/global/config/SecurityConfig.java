package com.ll.medium_mission.global.config;

import com.ll.medium_mission.global.util.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
                        requests.requestMatchers(mvc.pattern("/member/join")).permitAll()
                                .requestMatchers(mvc.pattern("/member/login")).permitAll()
                                .requestMatchers(mvc.pattern("/member/check")).permitAll()
                                .requestMatchers(mvc.pattern("/post")).permitAll()
                                .requestMatchers(mvc.pattern("/post/{postId}")).permitAll()
                                .requestMatchers(mvc.pattern("/post/{author}/posts")).permitAll()
                                .requestMatchers(mvc.pattern("/member/join")).permitAll()
                                .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
