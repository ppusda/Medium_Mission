package com.ll.medium_mission.post.controller;

import com.ll.medium_mission.global.util.ValidateUtil;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.post.dto.PostRequest;
import com.ll.medium_mission.post.dto.PostResponse;
import com.ll.medium_mission.post.dto.RecommendCheckResponse;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.service.PostRecommendService;
import com.ll.medium_mission.post.service.PostService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostRecommendService postRecommendService;
    private final MemberService memberService;
    private final ValidateUtil validateUtil;

    @GetMapping
    public Page<PostResponse> getPosts(@RequestParam("page") int page) {
        return postService.getPosts(page);
    }

    @GetMapping("/popular-posts")
    public Page<PostResponse> getPopularPosts() {
        return postService.getPosts(0);
    }

    @GetMapping("/write-posts")
    public Page<PostResponse> getMyPosts(@RequestParam("page") int page, Principal principal) {
        Member author = memberService.getMember(principal.getName());
        return postService.getAuthorsPosts(author, page);
    }

    @GetMapping("/{postId}")
    public PostResponse getPostDetail(@PathVariable("postId") Long postId) {
        return postService.getPostResponse(postId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> writePost(@Valid PostRequest postRequest, BindingResult bindingResult, Principal principal) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        Member author = memberService.getMember(principal.getName());
        postService.write(postRequest.title(), postRequest.content(), author);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{postId}")
    public void modifyPost(@PathVariable("postId") Long postId, PostRequest postRequest, Principal principal) {
        Member author = memberService.getMember(principal.getName());
        postService.modify(postId, postRequest.title(), postRequest.content(), author);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") Long postId, Principal principal) {
        Member author = memberService.getMember(principal.getName());
        postService.delete(postId, author);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/recommend")
    public void recommendPost(@PathVariable("postId") Long postId, Principal principal) {
        Post post = postService.getPost(postId);
        Member member = memberService.getMember(principal.getName());
        postRecommendService.recommend(post, member);
    }

    @GetMapping("/{postId}/recommend")
    public RecommendCheckResponse recommendCheck(@PathVariable("postId") Long postId, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        return RecommendCheckResponse.builder()
                .isRecommended(postRecommendService.recommendCheck(postId, member.getId()))
                .build();
    }
}
