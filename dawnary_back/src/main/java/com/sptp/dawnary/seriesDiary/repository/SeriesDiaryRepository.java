package com.sptp.dawnary.seriesDiary.repository;

import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesDiaryRepository extends JpaRepository<SeriesDiary, Long> {

    List<SeriesDiary> findBySeriesId(Long seriesId);
}
