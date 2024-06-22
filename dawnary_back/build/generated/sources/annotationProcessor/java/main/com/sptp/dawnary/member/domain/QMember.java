package com.sptp.dawnary.member.domain;

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

    private static final long serialVersionUID = -899915342L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final SetPath<com.sptp.dawnary.follow.domain.Follow, com.sptp.dawnary.follow.domain.QFollow> followers = this.<com.sptp.dawnary.follow.domain.Follow, com.sptp.dawnary.follow.domain.QFollow>createSet("followers", com.sptp.dawnary.follow.domain.Follow.class, com.sptp.dawnary.follow.domain.QFollow.class, PathInits.DIRECT2);

    public final SetPath<com.sptp.dawnary.follow.domain.Follow, com.sptp.dawnary.follow.domain.QFollow> followings = this.<com.sptp.dawnary.follow.domain.Follow, com.sptp.dawnary.follow.domain.QFollow>createSet("followings", com.sptp.dawnary.follow.domain.Follow.class, com.sptp.dawnary.follow.domain.QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final EnumPath<RoleType> role = createEnum("role", RoleType.class);

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

