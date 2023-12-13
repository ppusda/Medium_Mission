package com.ll.medium_mission.post.repository;

import com.ll.medium_mission.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
