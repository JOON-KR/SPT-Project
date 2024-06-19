package com.sptp.dawnary.series.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sptp.dawnary.like.domain.QLike;
import com.sptp.dawnary.series.domain.QSeries;
import com.sptp.dawnary.series.domain.Series;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeriesCustomRepositoryImpl implements SeriesCustomRepository {

    private final EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    private JPAQueryFactory getQueryFactory() {
        if (queryFactory == null) {
            queryFactory = new JPAQueryFactory(entityManager);
        }
        return queryFactory;
    }

    @Override
    public List<Series> findAllOrderByLatest() {
        QSeries series = QSeries.series;
        return getQueryFactory().selectFrom(series)
                .orderBy(series.regDate.desc())
                .fetch();
    }

    @Override
    public List<Series> findAllOrderByLikes() {
        QSeries series = QSeries.series;
        QLike like = QLike.like;

        return getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }

    @Override
    public List<Series> findAllOrderByLikesByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;

        return getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .where(like.regDate.between(startDate, endDate))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }

    @Override
    public List<Series> findMemberSeriesByLatest(Long memberId) {
        QSeries series = QSeries.series;
        return getQueryFactory().selectFrom(series)
                .where(series.member.id.eq(memberId))
                .orderBy(series.regDate.desc())
                .fetch();
    }

    @Override
    public List<Series> findMemberSeriesByLikes(Long memberId) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;

        return getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .where(series.member.id.eq(memberId))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();
    }
}
