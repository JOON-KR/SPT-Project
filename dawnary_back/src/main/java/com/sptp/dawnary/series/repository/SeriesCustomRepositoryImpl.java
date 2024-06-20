package com.sptp.dawnary.series.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sptp.dawnary.like.domain.QLike;
import com.sptp.dawnary.series.domain.QSeries;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.dto.SeriesDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<SeriesDto> findAllOrderByLatest() {
        QSeries series = QSeries.series;
        List<Series> seriesList = getQueryFactory().selectFrom(series)
                .orderBy(series.regDate.desc())
                .fetch();

        return seriesList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> findAllOrderByLikes() {
        QSeries series = QSeries.series;
        QLike like = QLike.like;
        List<Series> seriesList = getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();

        return seriesList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> findAllOrderByLikesByMonth(LocalDateTime startDate, LocalDateTime endDate) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;
        List<Series> seriesList = getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .where(like.regDate.between(startDate, endDate))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();

        return seriesList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> findMemberSeriesByLatest(Long memberId) {
        QSeries series = QSeries.series;
        List<Series> seriesList = getQueryFactory().selectFrom(series)
                .where(series.member.id.eq(memberId))
                .orderBy(series.regDate.desc())
                .fetch();

        return seriesList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> findMemberSeriesByLikes(Long memberId) {
        QSeries series = QSeries.series;
        QLike like = QLike.like;
        List<Series> seriesList = getQueryFactory().selectFrom(series)
                .leftJoin(series.likes, like)
                .where(series.member.id.eq(memberId))
                .groupBy(series.id)
                .orderBy(like.count().desc())
                .fetch();

        return seriesList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private SeriesDto toDto(Series series) {
        return SeriesDto.builder()
                .id(series.getId())
                .memberId(series.getMember().getId())
                .title(series.getTitle())
                .imagePath(series.getImagePath())
                .status(series.getStatus())
                .viewCnt(series.getViewCnt())
                .regDate(series.getRegDate())
                .build();
    }
}
