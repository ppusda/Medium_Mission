package com.ll.medium_mission.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.medium_mission.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void before() {
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원가입 요청을 하면 성공적으로 완료된다.")
    void joinMember() throws Exception {
        mockMvc.perform(post("/member/join")
                        .param("email", "test@naver.com")
                        .param("nickname", "test")
                        .param("password", "1234")
                        .param("passwordConfirm", "1234"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("필수항목 없이 회원가입 요청을 하면 에러 메시지가 출력된다.")
    void joinMemberWithoutForm() throws Exception {
        mockMvc.perform(post("/member/join")
                        .param("email", "")
                        .param("nickname", "test")
                        .param("password", "1234")
                        .param("passwordConfirm", "1234"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("필수항목 없이 회원가입 요청을 하면 에러 메시지가 출력된다.")
    void joinMemberDifferentPassword() throws Exception {
        mockMvc.perform(post("/member/join")
                        .param("email", "test@naver.com")
                        .param("nickname", "test")
                        .param("password", "1234")
                        .param("passwordConfirm", "12345"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 요청을 하면 성공적으로 완료된다.")
    void loginMember() throws Exception {
        String email = "test@naver.com";
        String password = "1234";

        memberService.join(email, null, passwordEncoder.encode(password));


        mockMvc.perform(post("/member/login")
                        .param("email", "test@naver.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void logoutMember() {
    }
}