package com.ll.medium_mission.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.medium_mission.member.dto.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 join 기능이 정상적으로 작동한다.")
    void join() {
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("test@naver.com")
                .password("1234")
                .passwordConfirm("1234")
                .build();

        memberService.join(memberJoinRequest.email(), memberJoinRequest.password());
    }
}