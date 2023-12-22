package com.ll.medium_mission.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostRecommend is a Querydsl query type for PostRecommend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostRecommend extends EntityPathBase<PostRecommend> {

    private static final long serialVersionUID = 1399397684L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostRecommend postRecommend = new QPostRecommend("postRecommend");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.ll.medium_mission.member.entity.QMember member;

    public final QPost post;

    public QPostRecommend(String variable) {
        this(PostRecommend.class, forVariable(variable), INITS);
    }

    public QPostRecommend(Path<? extends PostRecommend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostRecommend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostRecommend(PathMetadata metadata, PathInits inits) {
        this(PostRecommend.class, metadata, inits);
    }

    public QPostRecommend(Class<? extends PostRecommend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ll.medium_mission.member.entity.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

