package com.ll.medium_mission.global.provider;

import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

    public ResponseCookie createCookie(String accessToken) {

        return ResponseCookie.from("accessToken", accessToken)
                .domain("localhost")
                .path("/")
                .httpOnly(false) // javascript가 cookie 값에 접근하지 못하게 하는 설정.
                .secure(false)
                .maxAge(Duration.ofHours(3))
                .sameSite("Strict")
                .build();
    }
}
