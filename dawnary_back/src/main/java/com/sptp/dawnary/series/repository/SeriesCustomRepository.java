package com.sptp.dawnary.series.repository;

import com.sptp.dawnary.series.domain.Series;

import java.time.LocalDateTime;
import java.util.List;

public interface SeriesCustomRepository {
    List<Series> findAllOrderByLatest();
    List<Series> findAllOrderByLikes();
    List<Series> findAllOrderByLikesByMonth(LocalDateTime startDate, LocalDateTime endDate);
    List<Series> findMemberSeriesByLatest(Long memberId);
    List<Series> findMemberSeriesByLikes(Long memberId);
}
