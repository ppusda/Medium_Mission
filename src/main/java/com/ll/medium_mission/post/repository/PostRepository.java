package com.ll.medium_mission.post.repository;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByAuthor(Member author, Pageable pageable);
}
