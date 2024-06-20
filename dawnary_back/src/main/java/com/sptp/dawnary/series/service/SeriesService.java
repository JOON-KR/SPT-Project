package com.sptp.dawnary.series.service;

import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.diary.repository.DiaryRepository;
import com.sptp.dawnary.global.exception.DiaryNotFoundException;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.SeriesNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.dto.SeriesDto;
import com.sptp.dawnary.series.dto.SeriesFormDto;
import com.sptp.dawnary.series.repository.SeriesRepository;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import com.sptp.dawnary.seriesDiary.repository.SeriesDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final SeriesDiaryRepository seriesDiaryRepository;

    private final ModelMapper modelMapper;

    // 전체 시리즈 조회
    public List<SeriesDto> findAllSeriesByLatest() {
        return seriesRepository.findAllOrderByLatest().stream()
                .map(this::toSeriesDto)
                .toList();
    }

    // 명예의 전당(좋아요 많은 순 조회)
    public List<SeriesDto> findAllSeriesByLikes() {
        return seriesRepository.findAllOrderByLikes().stream()
                .map(this::toSeriesDto)
                .toList();
    }

    // 월별 시리즈
    public List<SeriesDto> findMonthlySeriesByLikes() {
        YearMonth currentMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        return seriesRepository.findAllOrderByLikesByMonth(startDate, endDate).stream()
                .map(this::toSeriesDto)
                .toList();
    }

    // 멤버 시리즈 최신순
    public List<SeriesDto> findMemberSeriesByLatest(Long memberId) {
        return seriesRepository.findMemberSeriesByLatest(memberId).stream()
                .map(this::toSeriesDto)
                .toList();
    }

    // 멤버 시리즈 좋아요순
    public List<SeriesDto> findMemberSeriesByLikes(Long memberId) {
        return seriesRepository.findMemberSeriesByLikes(memberId).stream()
                .map(this::toSeriesDto)
                .toList();
    }

    // 시리즈 저장
    public Series saveSeries(SeriesFormDto seriesFormDto) {
        Series series = modelMapper.map(seriesFormDto, Series.class);
        series.setMember(getMember());

        Series savedSeries = seriesRepository.save(series);

        saveSeriesDiaries(seriesFormDto, savedSeries);

        return savedSeries;
    }

    // 시리즈 수정
    public boolean updateSeries(Long seriesId, Series series) {
        series.setMember(getMember());
        if (seriesRepository.existsById(seriesId)) {
            series.setId(seriesId);
            seriesRepository.save(series);
            return true;
        }
        throw new SeriesNotFoundException("존재하지 않는 시리즈입니다.");
    }

    // 시리즈 삭제
    public boolean deleteSeries(Long seriesId) {
        if (seriesRepository.existsById(seriesId)) {
            seriesRepository.deleteById(seriesId);
            return true;
        }
        throw new SeriesNotFoundException("존재하지 않는 시리즈입니다.");
    }

    // 특정 시리즈 조회
    public SeriesDto findSeries(Long seriesId) {
        return seriesRepository.findById(seriesId)
                .map(this::toSeriesDto)
                .orElseThrow(() -> new SeriesNotFoundException("존재하지 않는 시리즈입니다."));
    }

    private List<DiaryDto> getDiaries(Long seriesId) {
        return seriesDiaryRepository.findBySeriesId(seriesId).stream()
                .map(SeriesDiary::getDiary)
                .map(DiaryDto::toDto)
                .toList();
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("멤버가 존재하지 않습니다."));
    }

    private void saveSeriesDiaries(SeriesFormDto seriesFormDto, Series series) {
        seriesFormDto.getDiaries().forEach(diaryId -> {

            if (diaryRepository.existsById(diaryId)) {
                SeriesDiary seriesDiary = SeriesDiary.builder()
                        .series(series)
                        .diary(diaryRepository.findById(diaryId).get())
                        .build();
                seriesDiaryRepository.save(seriesDiary);
            }

            throw new DiaryNotFoundException("다이어리가 존재하지 않습니다.");
        });
    }

    private SeriesDto toSeriesDto(Series series) {
        List<DiaryDto> diaries = getDiaries(series.getId());
        return SeriesDto.builder()
                .id(series.getId())
                .name(series.getMember().getName())
                .imagePath(series.getImagePath())
                .status(series.getStatus())
                .title(series.getTitle())
                .diaries(diaries)
                .regDate(series.getRegDate())
                .memberId(series.getMember().getId())
                .viewCnt(series.getViewCnt())
                .build();
    }
}