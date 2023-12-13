package com.ll.medium_mission.post.controller;

import com.ll.medium_mission.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public void getPosts() {

    }

    @GetMapping("/myList")
    public void getMyPosts() {

    }

    @GetMapping("/{postId}")
    public void getPostDetail() {

    }

    @PostMapping("/write")
    public void writePost() {

    }

    @PutMapping("/{postId}/modify")
    public void modifyPost() {

    }

    @PostMapping("/{postId}/delete")
    public void deletePost() {

    }
}
