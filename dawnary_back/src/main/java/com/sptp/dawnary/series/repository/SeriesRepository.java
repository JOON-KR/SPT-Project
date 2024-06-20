package com.sptp.dawnary.series.repository;

import com.sptp.dawnary.series.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesCustomRepository {
}
