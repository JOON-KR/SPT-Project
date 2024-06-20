package com.sptp.dawnary.follow.repository.repositoryImpl;

import static com.sptp.dawnary.follow.domain.QFollow.*;
import static com.sptp.dawnary.member.domain.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sptp.dawnary.follow.dto.response.FollowMemberResponse;
import com.sptp.dawnary.follow.repository.querydsl.FollowRepositoryCustom;
import com.sptp.dawnary.member.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FollowMemberResponse> findAllByFollowing(Member requestMember) {
        return findFollowing(requestMember);
    }

    @Override
    public List<FollowMemberResponse> findAllByFollower(Member requestMember) {
        return findFollower(requestMember);
    }

    private List<FollowMemberResponse> findFollowing(Member requestMember) {
        return queryFactory
                .select(Projections.constructor(FollowMemberResponse.class,
                                member.id, follow.id, member.name, member.email))
                .from(follow)
                .innerJoin(follow.following, member)
                .where(follow.follower.eq(requestMember))
                .orderBy(follow.id.desc())
                .fetch();
    }

    private List<FollowMemberResponse> findFollower(Member requestMember) {
        return queryFactory
                .select(Projections.constructor(FollowMemberResponse.class,
                        member.id, follow.id, member.name, member.email))
                .from(follow)
                .innerJoin(follow.follower, member)
                .where(follow.following.eq(requestMember))
                .orderBy(follow.id.desc())
                .fetch();
    }

}
