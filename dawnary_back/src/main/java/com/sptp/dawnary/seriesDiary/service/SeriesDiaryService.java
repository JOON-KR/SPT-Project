package com.sptp.dawnary.seriesDiary.service;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.diary.repository.DiaryRepository;
import com.sptp.dawnary.global.exception.DiaryNotFoundException;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import com.sptp.dawnary.seriesDiary.repository.SeriesDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SeriesDiaryService {

    private final SeriesDiaryRepository seriesDiaryRepository;
    private final DiaryRepository diaryRepository;

    public boolean saveSeriesDiary(Series series, List<Long> diaries) {
        for(Long diaryId : diaries) {
            if(!diaryRepository.existsById(diaryId))
                throw new DiaryNotFoundException("다이어리가 존재하지 않습니다.");
            SeriesDiary seriesDiary = SeriesDiary.builder()
                        .series(series)
                        .diary(diaryRepository.findById(diaryId).get())
                        .build();
            seriesDiaryRepository.save(seriesDiary);
        }
        return true;
    }

    public List<DiaryDto> findDiaryBySeries(Long seriesId) {
        List<SeriesDiary> seriesDiaries = seriesDiaryRepository.findBySeriesId(seriesId);
        return seriesDiaries.stream()
                .map(SeriesDiary::getDiary)
                .map(DiaryDto::toDto)
                .collect(Collectors.toList());
    }
}
