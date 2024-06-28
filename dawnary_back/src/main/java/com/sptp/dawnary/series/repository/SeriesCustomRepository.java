package com.sptp.dawnary.series.repository;

import com.sptp.dawnary.series.domain.Series;

import java.time.LocalDateTime;
import java.util.List;

public interface SeriesCustomRepository {
    List<Series> findAllOrderByLatest(Long loginMemberId);
    List<Series> findAllOrderByLikes(Long loginMemberId);
    List<Series> findAllOrderByLikesByMonth(Long loginMemberId, LocalDateTime startDate, LocalDateTime endDate);

    List<Series> findMemberSeriesByLatest(Long loginMemberId, Long memberId);
    List<Series> findMemberSeriesByLikes(Long loginMemberId, Long memberId);

    List<Series> findFollowSeriesByLatest(Long loginMemberId);
}
