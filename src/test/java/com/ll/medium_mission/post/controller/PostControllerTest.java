package com.ll.medium_mission.post.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ll.medium_mission.global.provider.JwtTokenProvider;
import com.ll.medium_mission.member.dto.MemberLoginResponse;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.post.service.PostService;
import com.ll.medium_mission.token.service.TokenService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PostService postService;

    @BeforeEach
    public void before() {
        tokenService.deleteAll();
    }

    @Test
    @DisplayName("1 페이지에 대한 글 조회가 정상적으로 이루어진다.")
    void getPosts() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        postService.write("제목입니다.", "내용입니다.", member);

        mockMvc.perform(get("/post?page=0"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("내가 쓴 글에 대한 1 페이지에 대한 글 조회가 정상적으로 이루어진다.")
    void getMyPosts() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        MemberLoginResponse memberLoginResponse = memberService.login("test@naver.com", "1234");

        postService.write("제목입니다.", "내용입니다.", member);

        mockMvc.perform(get("/post/myList?page=0")
                        .cookie(new Cookie("accessToken", memberLoginResponse.accessToken())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 상세 조회가 정상적으로 이루어진다.")
    void getPostDetail() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        MemberLoginResponse memberLoginResponse = memberService.login("test@naver.com", "1234");

        postService.write("제목입니다.", "내용입니다.", member);

        mockMvc.perform(get("/post/1")
                        .cookie(new Cookie("accessToken", memberLoginResponse.accessToken())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성이 정상적으로 이루어진다.")
    void writePost() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        MemberLoginResponse memberLoginResponse = memberService.login("test@naver.com", "1234");

        mockMvc.perform(post("/post/write")
                        .cookie(new Cookie("accessToken", memberLoginResponse.accessToken()))
                        .param("title", "제목입니다!!")
                        .param("content", "내용입니다!!"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("권한이 없는 상태에서는 글 작성을 할 수 없다.")
    void writePostWithoutPrincipal() throws Exception {
        mockMvc.perform(post("/post/write")
                        .param("title", "제목입니다!!")
                        .param("content", "내용입니다!!"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("글 수정이 정상적으로 이루어진다.")
    void modifyPost() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        MemberLoginResponse memberLoginResponse = memberService.login("test@naver.com", "1234");

        mockMvc.perform(put("/post/1/modify")
                        .cookie(new Cookie("accessToken", memberLoginResponse.accessToken()))
                        .param("title", "제목입니다~")
                        .param("content", "내용입니다~"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("권한이 없는 상태에서는 글 수정을 할 수 없다.")
    void modifyPostWithoutPrincipal() throws Exception {
        mockMvc.perform(put("/post/1/modify")
                        .param("title", "제목입니다~")
                        .param("content", "내용입니다~"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("글 삭제가 정상적으로 이루어진다.")
    void deletePost() throws Exception {
        Member member = memberService.getMember("test@naver.com");
        MemberLoginResponse memberLoginResponse = memberService.login("test@naver.com", "1234");

        mockMvc.perform(delete("/post/1/delete")
                        .cookie(new Cookie("accessToken", memberLoginResponse.accessToken())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("권한이 없는 상태에서는 글 삭제를 할 수 없다.")
    void deletePostWithoutPrincipal() throws Exception {
        mockMvc.perform(delete("/post/1/delete"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}