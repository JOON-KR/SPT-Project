package com.sptp.dawnary.series.controller;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.dto.SeriesFormDto;
import com.sptp.dawnary.series.service.SeriesService;
import com.sptp.dawnary.seriesDiary.service.SeriesDiaryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final SeriesDiaryService seriesDiaryService;

    private final ModelMapper modelMapper;

    // 모든 시리즈 조회(최신 순)
    @GetMapping("/all")
    public ResponseEntity<?> getAllSeries() {
        return new ResponseEntity<>(seriesService.findAllSeriesByLatest(), HttpStatus.OK);
    }

    // 모든 시리즈 조회(좋아요 순)
    @GetMapping("/best")
    public ResponseEntity<?> getBestSeries() {
        return new ResponseEntity<>(seriesService.findAllSeriesByLikes(), HttpStatus.OK);
    }

    // 모든 시리즈 조회(이달의 좋아요순)
    @GetMapping("/month")
    public ResponseEntity<?> getMonthlyBestSeries() {
        return new ResponseEntity<>(seriesService.findMonthlySeriesByLikes(), HttpStatus.OK);
    }

    // 멤버 시리즈 조회(최신 순)
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getMemberSeries(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(seriesService.findMemberSeriesByLatest(memberId), HttpStatus.OK);
    }

    // 멤버 시리즈 조회(좋아요 순)
    @GetMapping("/member/best/{memberId}")
    public ResponseEntity<?> getMemberBestSeries(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(seriesService.findMemberSeriesByLikes(memberId), HttpStatus.OK);
    }

    // 특정 시리즈 조회
    @GetMapping("/{seriesId}")
    public ResponseEntity<?> getDetailSeries(@PathVariable("seriesId") Long seriesId) {
        List<DiaryDto> diaries = seriesDiaryService.findDiaryBySeries(seriesId);
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    // 시리즈 등록
    @PostMapping
    public ResponseEntity<?> saveSeries(@RequestBody SeriesFormDto series) {
        Series entity = modelMapper.map(series, Series.class);
        Series result = seriesService.saveSeries(entity);

        if(result == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(seriesDiaryService.saveSeriesDiary(result, series.getDiaries()))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 시리즈 수정
//    @PutMapping("/{seriesId}")
//    public ResponseEntity<?> updateSeries(@PathVariable("seriesId") Long seriesId) {
//
//    }

    // 시리즈 삭제
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<?> removeSeries(@PathVariable("seriesId") Long seriesId) {
        boolean result = seriesService.deleteSeries(seriesId);

        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
