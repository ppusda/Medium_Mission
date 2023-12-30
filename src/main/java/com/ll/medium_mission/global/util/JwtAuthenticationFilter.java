package com.ll.medium_mission.global.util;

import com.ll.medium_mission.global.provider.CookieProvider;
import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.token.entity.Token;
import com.ll.medium_mission.token.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
    private final MemberService memberService;
    private final TokenService tokenService;

    private String getTokenFromRequest(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getTokenFromRequest(request, "accessToken");

        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenProvider.validateToken(accessToken)){
            String memberId = jwtTokenProvider.getClaims(accessToken).getSubject();
            String refreshToken = getTokenFromRequest(request, "refreshToken");
            Token token = tokenService.getToken(memberId);

            if (!StringUtils.hasText(refreshToken)) {
                tokenService.deleteToken(memberId);
                throw new AuthenticationException("잘못된 접근입니다. 재로그인이 필요합니다.");
            }

            if (!refreshToken.equals(token.getRefreshToken()) || !jwtTokenProvider.validateToken(token.getRefreshToken())) {
                throw new AuthenticationException("재로그인이 필요합니다.");
            }

            List<String> memberAuthorities = memberService.getAuthorities(memberId);
            accessToken = jwtTokenProvider.createAccessToken(memberId, memberAuthorities);
            refreshToken = jwtTokenProvider.createRefreshToken(memberId);

            tokenService.deleteToken(memberId);
            tokenService.register(refreshToken, Long.valueOf(memberId));

            response.setHeader(HttpHeaders.SET_COOKIE, cookieProvider.createAccessTokenCookie(accessToken).toString());
            response.setHeader(HttpHeaders.SET_COOKIE, cookieProvider.createRefreshTokenCookie(refreshToken).toString());
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
