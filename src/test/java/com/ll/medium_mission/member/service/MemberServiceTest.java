package com.ll.medium_mission.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.medium_mission.member.dto.MemberJoinRequest;
import com.ll.medium_mission.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("dev")
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void before() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원 join 기능이 정상적으로 작동한다.")
    void join() {
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("test@naver.com")
                .password("1234")
                .passwordConfirm("1234")
                .build();

        memberService.join(memberJoinRequest.email(), null, memberJoinRequest.password());

        assertEquals(memberRepository.count(), 1L);
    }

    @Test
    @DisplayName("회원 login 기능이 정상적으로 작동한다.")
    void login() {
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("test@naver.com")
                .password("1234")
                .passwordConfirm("1234")
                .build();

        memberService.join(memberJoinRequest.email(), null, passwordEncoder.encode(memberJoinRequest.password()));
        memberService.login(memberJoinRequest.email(), memberJoinRequest.password());
    }
}