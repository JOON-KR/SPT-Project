package com.sptp.dawnary.series.controller;

import com.sptp.dawnary.series.dto.SeriesRequest;
import com.sptp.dawnary.series.dto.SeriesResponse;
import com.sptp.dawnary.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;

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

    // 멤버 시리즈 조회(좋아요 순)
    @GetMapping("/follow")
    public ResponseEntity<?> getFollowSeries() {
        return new ResponseEntity<>(seriesService.findFollowSeriesByLatest(), HttpStatus.OK);
    }

    // 특정 시리즈 조회
    @GetMapping("/{seriesId}")
    public ResponseEntity<?> getDetailSeries(@PathVariable("seriesId") Long seriesId) {
        return new ResponseEntity<>(seriesService.findSeries(seriesId), HttpStatus.OK);
    }

    // 시리즈 등록
    @PostMapping
    public ResponseEntity<?> saveSeries(@RequestBody SeriesRequest seriesDto) {
        SeriesResponse series = seriesService.saveSeries(seriesDto);

        return new ResponseEntity<>(series, HttpStatus.CREATED);
    }

    // 시리즈 삭제
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<?> removeSeries(@PathVariable("seriesId") Long seriesId) {
        seriesService.deleteSeries(seriesId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
