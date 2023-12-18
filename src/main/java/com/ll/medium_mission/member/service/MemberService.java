package com.ll.medium_mission.member.service;

import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.dto.MemberLoginResponse;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.repository.MemberRepository;
import com.ll.medium_mission.token.service.TokenService;
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
    private final TokenService tokenService;

    @Transactional
    public void join(String email, String nickname, String password) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member joinMember = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(joinMember);
    }

    @Transactional
    public MemberLoginResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        checkPassword(member, password);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()));

        tokenService.register(refreshToken, member.getId());

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public Member getMember(String id) {
        Optional<Member> member = memberRepository.findById(Long.parseLong(id));
        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return member.get();
    }

    @Transactional
    public Member getMemberByNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return member.get();
    }

    public void logout(String memberId) {
        tokenService.deleteToken(memberId);
    }

    private void checkPassword(Member member, String password) {
        if (passwordEncoder.matches(member.getPassword(), password)) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }
    }

    public void deleteAll() { // 테스트 전용 메서드
        memberRepository.deleteAll();
    }

}
