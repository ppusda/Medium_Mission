package com.ll.medium_mission.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1883322852L;

    public static final QMember member = new QMember("member1");

    public final com.ll.medium_mission.global.entity.QBaseEntity _super = new com.ll.medium_mission.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.ll.medium_mission.post.entity.Post, com.ll.medium_mission.post.entity.QPost> posts = this.<com.ll.medium_mission.post.entity.Post, com.ll.medium_mission.post.entity.QPost>createList("posts", com.ll.medium_mission.post.entity.Post.class, com.ll.medium_mission.post.entity.QPost.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

