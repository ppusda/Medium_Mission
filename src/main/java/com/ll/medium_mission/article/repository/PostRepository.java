package com.ll.medium_mission.article.repository;

import com.ll.medium_mission.article.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
