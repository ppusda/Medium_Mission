package com.ll.medium_mission.token.service;

import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.token.entity.Token;
import com.ll.medium_mission.token.repository.TokenRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional
    public Token getToken(String memberId) {
        Optional<Token> token = tokenRepository.findByMemberId(Long.parseLong(memberId));

        if (token.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 인증 값입니다.");
        }

        return token.get();
    }

    @Transactional
    public void deleteToken(String memberId) {
        Token token = getToken(memberId);
        tokenRepository.deleteById(token.getId());
    }

    @Transactional
    public void register(String refreshToken, Long memberId) {
        Token token = Token.builder()
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();

        tokenRepository.save(token);
    }

    public void deleteAll() { // 테스트용 메서드
        tokenRepository.deleteAll();
    }
}
