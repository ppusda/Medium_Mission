package com.ll.medium_mission.global.util;

import com.ll.medium_mission.global.provider.CookieProvider;
import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.token.entity.Token;
import com.ll.medium_mission.token.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final TokenService tokenService;

    private String getJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getJwtFromRequest(request);

        if (StringUtils.hasText(accessToken) && !jwtTokenProvider.validateToken(accessToken)) {
            String memberId = jwtTokenProvider.getClaims(accessToken).getSubject();
            Token token = tokenService.getToken(memberId);

            if (!jwtTokenProvider.validateToken(token.getRefreshToken())) {
                throw new AuthenticationException("로그인이 필요합니다.");
            }

            accessToken = jwtTokenProvider.createAccessToken(memberId);

            response.setHeader(HttpHeaders.SET_COOKIE, cookieProvider.createCookie(accessToken).toString());
        }

        if (StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
