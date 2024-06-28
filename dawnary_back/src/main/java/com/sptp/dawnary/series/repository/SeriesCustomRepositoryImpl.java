package com.sptp.dawnary.series.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sptp.dawnary.follow.domain.QFollow;
import com.sptp.dawnary.like.domain.QLike;
import com.sptp.dawnary.series.domain.QSeries;
import com.sptp.dawnary.series.domain.Series;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeriesCustomRepositoryImpl implements SeriesCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Series> findAllOrderByLatest(Long loginMemberId) {
        QSeries series = QSeries.series;
        return queryFactory.selectFrom(series)
                .where(series.member.id.ne(loginMemberId).and(series.status.eq(1))
                        .or(series.member.id.eq(loginMemberId)))
                .orderBy(series.regDate.desc())
                .fetch();
    }

    @Override
    public List<Series> findAllOrderByLikes(Long loginMemberId) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;
        return queryFactory.selectFrom(series)
                .leftJoin(series.likes, like)
                .where(series.member.id.ne(loginMemberId).and(series.status.eq(1))
                        .or(series.member.id.eq(loginMemberId)))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }

    @Override
    public List<Series> findAllOrderByLikesByMonth(Long loginMemberId, LocalDateTime startDate, LocalDateTime endDate) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;
        return queryFactory.selectFrom(series)
                .leftJoin(series.likes, like)
                .where(like.regDate.between(startDate, endDate))
                .where(series.member.id.ne(loginMemberId).and(series.status.eq(1))
                        .or(series.member.id.eq(loginMemberId)))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }

    @Override
    public List<Series> findMemberSeriesByLatest(Long loginMemberId, Long memberId) {
        QSeries series = QSeries.series;
        return queryFactory.selectFrom(series)
                .where(series.member.id.eq(memberId))
                .where(series.member.id.ne(loginMemberId).and(series.status.eq(1))
                        .or(series.member.id.eq(loginMemberId)))
                .orderBy(series.regDate.desc())
                .fetch();
    }

    @Override
    public List<Series> findMemberSeriesByLikes(Long loginMemberId, Long memberId) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;

        return queryFactory.selectFrom(series)
                .leftJoin(series.likes, like)
                .where(series.member.id.eq(memberId))
                .where(series.member.id.ne(loginMemberId).and(series.status.eq(1))
                        .or(series.member.id.eq(loginMemberId)))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }

    @Override
    public List<Series> findFollowSeriesByLatest(Long loginMemberId) {
        QSeries series = QSeries.series;
        QFollow follow = QFollow.follow;
        return queryFactory.selectFrom(series)
                .where(series.member.id.in(
                        JPAExpressions.select(follow.following.id)
                                .from(follow)
                                .where(follow.follower.id.eq(loginMemberId))
                ))
                .orderBy(series.regDate.desc())
                .fetch();
    }

}
