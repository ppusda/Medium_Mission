package com.ll.medium_mission.post.repository;

import com.ll.medium_mission.post.entity.PostRecommend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

    Optional<PostRecommend> findByPostIdAndMemberId(Long postId, Long memberId);

}
