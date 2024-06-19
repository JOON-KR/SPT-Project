package com.sptp.dawnary.series.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.sptp.dawnary.series.domain.Series;

public interface SeriesCustomRepository {

    // 전체 시리즈 최신순
    List<Series> findAllOrderByLatest();

    // 전체 시리즈 좋아요순
    List<Series> findAllOrderByLikes();

    // 전체 시리즈 월별 좋아요 순
    List<Series> findAllOrderByLikesByMonth(LocalDateTime startDate, LocalDateTime endDate);

    // 멤버 시리즈 최신순
    List<Series> findMemberSeriesByLatest(Long memberId);

    // 멤버 시리즈 좋아요순
    List<Series> findMemberSeriesByLikes(Long memberId);


}
