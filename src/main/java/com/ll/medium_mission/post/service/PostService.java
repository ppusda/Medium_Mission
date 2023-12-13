package com.ll.medium_mission.post.service;

import com.ll.medium_mission.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write() {

    }
}
