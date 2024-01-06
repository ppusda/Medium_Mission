package com.ll.medium_mission.member.service;

import com.ll.medium_mission.global.exception.AlreadyExistMemberException;
import com.ll.medium_mission.global.exception.NotExistMemberException;
import com.ll.medium_mission.global.exception.NotIncorrectAccountException;
import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.dto.MemberLoginResponse;
import com.ll.medium_mission.member.dto.MemberResponse;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.repository.MemberRepository;
import com.ll.medium_mission.member.util.MemberRole;
import com.ll.medium_mission.post.service.PostService;
import com.ll.medium_mission.token.service.TokenService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final TokenService tokenService;
    private final PostService postService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void join(String email, String nickname, String password) {
        checkDuplicateEmail(email);
        nickname = getNickname(email, nickname);

        Member joinMember = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .isPaid(false)
                .build();

        memberRepository.save(joinMember);
    }

    @Transactional
    public MemberLoginResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NotExistMemberException::new);

        checkPassword(member, password);

        String memberId = String.valueOf(member.getId());
        List<String> memberAuthorities = getRoles(memberId);

        String accessToken = jwtTokenProvider.createAccessToken(memberId, memberAuthorities);
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);

        tokenService.register(refreshToken, member.getId());

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void modify(String memberId, String nickname, String password, String newPassword) {
        Member member = getMember(memberId);

        checkPassword(member, password);

        if (!nickname.isBlank() && !member.getNickname().equals(nickname)) {
            member.modifyNickname(nickname);
        }

        if (!newPassword.isBlank()) {
            member.modifyPassword(passwordEncoder.encode(newPassword));
        }
    }

    @Transactional
    public Member getMember(String id) {
        return memberRepository.findById(Long.parseLong(id))
                .orElseThrow(NotExistMemberException::new);
    }

    @Transactional
    public Member getMemberByNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isEmpty()) {
            throw new NotExistMemberException();
        }
        return member.get();
    }

    public void logout(String memberId) {
        tokenService.deleteToken(memberId);
    }

    @Transactional
    public MemberResponse getMemberResponse(String memberId) {
        Member member = getMember(memberId);
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    @Transactional
    public List<String> getRoles(String memberId) {
        Member member = getMember(memberId);

        List<String> authorities = new ArrayList<>();
        authorities.add(MemberRole.USER.getRole());

        if (member.getIsPaid()) {
            authorities.add(MemberRole.PAID.getRole());
        }

        return authorities;
    }

    private void checkDuplicateEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new AlreadyExistMemberException();
        }
    }

    private void checkPassword(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new NotIncorrectAccountException();
        }
    }

    private String getNickname(String email, String nickname) {
        if (nickname == null || nickname.isBlank()) {
            nickname = email.split("@")[0];
        }
        checkDuplicateNickname(nickname);
        return nickname;
    }

    private void checkDuplicateNickname(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new AlreadyExistMemberException();
        }
    }

    @Transactional
    public void registerMembership(String memberId) {
        Member member = getMember(memberId);
        member.setMembership(true);
    }

    @Transactional
    public void cancelMembership (String memberId) {
        Member member = getMember(memberId);
        postService.cancelMembershipPost(member.getPosts());
        member.setMembership(false);
    }

    public void deleteAll() { // 테스트 전용 메서드
        memberRepository.deleteAll();
    }

}
