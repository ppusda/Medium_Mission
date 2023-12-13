package com.ll.medium_mission.global.provider;

import com.ll.medium_mission.global.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private SecretKey key;

    public JwtTokenProvider(AppConfig appConfig) {
        this.key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
    }

    public String createAccessToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 30)))
                .setIssuedAt(now)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(email)
                .signWith(key)
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 60 * 3)))
                .setIssuedAt(now)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(JwtException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "", new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

}
