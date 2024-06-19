package com.sptp.dawnary.series.service;

import java.util.List;

import com.sptp.dawnary.series.SeriesRepository;
import com.sptp.dawnary.series.domain.Series;

public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    // 전체 시리즈 조회
    public List<Series> findAllSeries() {
        return seriesRepository.findAll();
    }

    // 명예의 전당(좋아요 많은 순 조회

}
