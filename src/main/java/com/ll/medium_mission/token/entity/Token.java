package com.ll.medium_mission.token.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @Indexed
    private String accessToken;

    private Long memberId;

    @Builder
    public Token(String refreshToken, String accessToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.memberId = memberId;
    }

    public void modifyAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
