package com.ll.medium_mission.article.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

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
