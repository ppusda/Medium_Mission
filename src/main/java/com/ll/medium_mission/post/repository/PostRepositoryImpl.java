package com.ll.medium_mission.post.repository;

import com.ll.medium_mission.member.entity.QMember;
import com.ll.medium_mission.post.entity.Post;
import com.ll.medium_mission.post.entity.QPost;
import com.ll.medium_mission.post.entity.QPostRecommend;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> getHotPosts(Pageable pageable) {
        QPost post = QPost.post;
        QPostRecommend recommend = QPostRecommend.postRecommend;

        LocalDateTime week = LocalDateTime.now().minusWeeks(1);

        List<Post> hotPostList = jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.postRecommends, recommend)
                .where(post.modifiedDate.between(week, LocalDateTime.now()),
                        post.modifiedDate.after(week))
                .groupBy(post.id)
                .orderBy(recommend.id.count().desc())
                .limit(5)
                .fetch();

        return new PageImpl<>(hotPostList, pageable, 5);
    }

    @Override
    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        QPost post = QPost.post;
        QMember postAuthor = new QMember("postAuthor");

        List<Post> searchedPostList = jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.author, postAuthor)
                .where(likeKeyword(post.title, keyword)
                        .or(likeKeyword(post.content, keyword))
                        .or(likeKeyword(postAuthor.nickname, keyword))
                ).distinct()
                .fetch();

        return new PageImpl<>(searchedPostList, pageable, searchedPostList.size());
    }

    private BooleanExpression likeKeyword(StringPath path, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return path.containsIgnoreCase(keyword);
    }
}
