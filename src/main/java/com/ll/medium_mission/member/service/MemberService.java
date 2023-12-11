package com.ll.medium_mission.member.service;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(String email, String password) {
        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }

}
