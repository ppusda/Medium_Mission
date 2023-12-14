package com.ll.medium_mission.token.service;

import com.ll.medium_mission.token.entity.Token;
import com.ll.medium_mission.token.repository.TokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token getToken(String accessToken) {
        Optional<Token> token = tokenRepository.findByAccessToken(accessToken);

        if (token.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 인증 값입니다.");
        }

        return token.get();
    }

    public void deleteToken(String accessToken) {
        tokenRepository.deleteByAccessToken(accessToken);
    }

    public void register(String accessToken, String refreshToken, Long memberId) {
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build();

        tokenRepository.save(token);
    }

    public void modify(Token token, String accessToken) {
        token.modifyAccessToken(accessToken);
        tokenRepository.save(token);
    }

    public void deleteAll() { // 테스트용 메서드
        tokenRepository.deleteAll();
    }
}
