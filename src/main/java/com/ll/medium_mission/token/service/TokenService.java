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

    public Token getToken(String memberId) {
        Optional<Token> token = tokenRepository.findByMemberId(Long.parseLong(memberId));

        if (token.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 인증 값입니다.");
        }

        return token.get();
    }

    public void deleteToken(String memberId) {
        Token token = getToken(memberId);
        tokenRepository.deleteById(token.getId());
    }

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
