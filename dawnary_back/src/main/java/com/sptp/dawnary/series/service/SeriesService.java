package com.sptp.dawnary.series.service;

import com.sptp.dawnary.diary.dto.DiaryResponse;
import com.sptp.dawnary.diary.repository.DiaryRepository;
import com.sptp.dawnary.elastic.document.SeriesDocument;
import com.sptp.dawnary.elastic.repository.SeriesElasticRepository;
import com.sptp.dawnary.global.exception.DiaryNotFoundException;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.SeriesNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.dto.SeriesRequest;
import com.sptp.dawnary.series.dto.SeriesResponse;
import com.sptp.dawnary.series.repository.SeriesRepository;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import com.sptp.dawnary.seriesDiary.repository.SeriesDiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final SeriesDiaryRepository seriesDiaryRepository;
    private final SeriesElasticRepository seriesElasticRepository;


    // 전체 시리즈 조회
    public List<SeriesResponse> findAllSeriesByLatest() {
        return seriesRepository.findAllOrderByLatest(MemberInfo.getMemberId()).stream()
                .map(series -> SeriesResponse.toResponse(series, getDiaries(series.getId())))
                .toList();
    }

    // 명예의 전당(좋아요 많은 순 조회)
    public List<SeriesResponse> findAllSeriesByLikes() {
        return seriesRepository.findAllOrderByLikes(MemberInfo.getMemberId()).stream()
                .map(series -> SeriesResponse.toResponse(series, getDiaries(series.getId())))
                .toList();
    }

    // 월별 시리즈
    public List<SeriesResponse> findMonthlySeriesByLikes() {
        YearMonth currentMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        return seriesRepository.findAllOrderByLikesByMonth(MemberInfo.getMemberId(), startDate, endDate).stream()
                .map(series -> SeriesResponse.toResponse(series, getDiaries(series.getId())))
                .toList();
    }

    // 멤버 시리즈 최신순
    public List<SeriesResponse> findMemberSeriesByLatest(Long memberId) {
        return seriesRepository.findMemberSeriesByLatest(MemberInfo.getMemberId(), memberId).stream()
                .map(series -> SeriesResponse.toResponse(series, getDiaries(series.getId())))
                .toList();
    }

    // 멤버 시리즈 좋아요순
    public List<SeriesResponse> findMemberSeriesByLikes(Long memberId) {
        return seriesRepository.findMemberSeriesByLikes(MemberInfo.getMemberId(), memberId).stream()
                .map(series -> SeriesResponse.toResponse(series, getDiaries(series.getId())))
                .toList();
    }

    // 시리즈 저장
    public SeriesResponse saveSeries(SeriesRequest seriesFormDto) {
        Series series = Series.toEntity(seriesFormDto);
        series.setMember(getMember());

        seriesRepository.save(series);

        saveSeriesDiaries(seriesFormDto, series);

        //document에 저장
        SeriesDocument sd = SeriesDocument.from(series);
        seriesElasticRepository.save(sd);
        return SeriesResponse.toResponse(series, getDiaries(series.getId()));
    }

    // 시리즈 삭제
    public void deleteSeries(Long seriesId) {
        if (seriesRepository.existsById(seriesId)) {
            seriesRepository.deleteById(seriesId);
            //document에서 삭제
            seriesElasticRepository.deleteById(seriesId);
        }

        throw new SeriesNotFoundException();
    }

    // 특정 시리즈 조회
    public SeriesResponse findSeries(Long seriesId) {
        Optional<Series> series = seriesRepository.findById(seriesId);

        if(series.isEmpty()) throw new SeriesNotFoundException();

        List<DiaryResponse> diaries = getDiaries(series.get().getId());

        series.get().setViewCnt(series.get().getViewCnt() + 1);

        seriesRepository.save(series.get());

        return SeriesResponse.toResponse(series.get(), diaries);
    }

    private void saveSeriesDiaries(SeriesRequest seriesRequest, Series series) {

        seriesRequest.diaries().forEach(diaryId -> {

            log.info("diaryId = {}", diaryId);

            if (!diaryRepository.existsById(diaryId)) {
                throw new DiaryNotFoundException();
            }

            SeriesDiary seriesDiary = SeriesDiary.builder()
                    .series(series)
                    .diary(diaryRepository.findById(diaryId).get())
                    .build();
            seriesDiaryRepository.save(seriesDiary);

        });
    }
    private List<DiaryResponse> getDiaries(Long seriesId) {
        return seriesDiaryRepository.findBySeriesId(seriesId).stream()
                .map(SeriesDiary::getDiary)
                .map(DiaryResponse::toResponse)
                .toList();
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
    }

}
