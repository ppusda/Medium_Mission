package com.ll.medium_mission.global.provider;

import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    public ResponseCookie createAccessTokenCookie(String accessToken) {

        return ResponseCookie.from("accessToken", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofMinutes(30))
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken) {

        return ResponseCookie.from("refreshToken", refreshToken)
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofDays(1))
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie removeToken(String cookieName) {
        return ResponseCookie.from(cookieName, null)
                .domain("localhost")
                .path("/")
                .maxAge(0)
                .build();
    }
}
