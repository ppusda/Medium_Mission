package com.ll.medium_mission.post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ll.medium_mission.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PostRecommend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public PostRecommend(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
