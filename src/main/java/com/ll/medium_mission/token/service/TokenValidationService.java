package com.ll.medium_mission.token.service;

import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.token.entity.Token;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final JwtTokenProvider jwtTokenProvider;

    public void validateRefreshTokenNotEmpty(String memberId, String refreshToken)
            throws AuthenticationException {
        if (!StringUtils.hasText(refreshToken)) {
            tokenService.deleteToken(memberId);
            throw new AuthenticationException("잘못된 접근입니다. 재로그인이 필요합니다.");
        }
    }

    public void validateRefreshToken(String memberId, String refreshToken)
            throws AuthenticationException {
        Token token = tokenService.getToken(memberId);

        if (!refreshToken.equals(token.getRefreshToken()) || !jwtTokenProvider.validateToken(token.getRefreshToken())) {
            throw new AuthenticationException("재로그인이 필요합니다.");
        }
    }

    public String refreshTokens(String memberId) {
        List<String> memberAuthorities = memberService.getRoles(memberId);
        updateRefreshToken(memberId, jwtTokenProvider.createRefreshToken(memberId));

        return jwtTokenProvider.createAccessToken(memberId, memberAuthorities);
    }

    public void updateRefreshToken(String memberId, String refreshToken) {
        tokenService.deleteToken(memberId);
        tokenService.register(refreshToken, Long.valueOf(memberId));
    }

}
