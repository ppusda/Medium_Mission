package com.ll.medium_mission.member.service;

import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.dto.MemberLoginResponse;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.repository.MemberRepository;
import com.ll.medium_mission.token.entity.Token;
import com.ll.medium_mission.token.repository.TokenRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    @Transactional
    public void join(String email, String password) {
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public MemberLoginResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        checkPassword(member, password);

        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        saveToken(accessToken, refreshToken, member.getId());

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public Member getMember(String email) {
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return member.get();
    }

    public void logout(String token) {
        tokenRepository.deleteByAccessToken(token);
    }

    private void checkPassword(Member member, String password) {
        if (passwordEncoder.matches(member.getPassword(), password)) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
    }

    private void saveToken(String accessToken, String refreshToken, Long memberId) {
        tokenRepository.save(Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(memberId)
                .build());
    }

    public void deleteAll() { // 테스트 전용 메서드
        memberRepository.deleteAll();
    }

}
