package com.sptp.dawnary.series.repository;

import com.sptp.dawnary.series.dto.SeriesDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SeriesCustomRepository {
    List<SeriesDto> findAllOrderByLatest();
    List<SeriesDto> findAllOrderByLikes();
    List<SeriesDto> findAllOrderByLikesByMonth(LocalDateTime startDate, LocalDateTime endDate);
    List<SeriesDto> findMemberSeriesByLatest(Long memberId);
    List<SeriesDto> findMemberSeriesByLikes(Long memberId);
}
