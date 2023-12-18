package com.ll.medium_mission.post.service;

import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.entity.PostRecommend;
import com.ll.medium_mission.post.repository.PostRecommendRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRecommendService {

    private final PostRecommendRepository postRecommendRepository;

    @Transactional
    public void recommend(Post post, Member member) {
        Optional<PostRecommend> optionalPostRecommend = findPostRecommend(post, member);

        if (optionalPostRecommend.isPresent()) {
            postRecommendRepository.delete(optionalPostRecommend.get());
        } else {
            PostRecommend postRecommend = createPostRecommend(post, member);
            postRecommendRepository.save(postRecommend);
        }
    }

    private Optional<PostRecommend> findPostRecommend(Post post, Member member) {
        return postRecommendRepository.findByPostIdAndMemberId(post.getId(), member.getId());
    }

    private PostRecommend createPostRecommend(Post post, Member member) {
        return PostRecommend.builder()
                .post(post)
                .member(member)
                .build();
    }

    public boolean recommendCheck(Long postId, Long memberId) {
        return postRecommendRepository.findByPostIdAndMemberId(postId, memberId).isPresent();
    }

}
