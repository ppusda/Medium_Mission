package com.ll.medium_mission.post.repository;

import com.ll.medium_mission.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> getHotPosts(Pageable pageable);
    Page<Post> searchPosts(String keyword, Pageable pageable);
}
