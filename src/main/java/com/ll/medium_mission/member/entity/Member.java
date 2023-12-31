package com.ll.medium_mission.member.entity;

import com.ll.medium_mission.global.entity.BaseEntity;
import com.ll.medium_mission.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity @Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 30)
    private String nickname;

    @Column
    private String password;

    @Column
    private Boolean isPaid;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @Builder
    public Member(String email, String nickname, String password, Boolean isPaid) {
        this.email = email;
        this.nickname = (nickname == null || nickname.isBlank()) ? email.split("@")[0] : nickname;
        this.password = password;
        this.isPaid = isPaid;
    }

    public void setMembership(Boolean paid) {
        isPaid = paid;
    }
}
