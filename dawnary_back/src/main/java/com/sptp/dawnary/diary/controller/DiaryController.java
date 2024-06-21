package com.sptp.dawnary.diary.controller;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.diary.dto.DiaryFormDto;
import com.sptp.dawnary.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final ModelMapper modelMapper;

    // 모든 다이어리 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getAllDiaries(@PathVariable("memberId") Long memberId) {
        List<DiaryDto> diaries = diaryService.findAllDiaries(memberId);
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }

    // 특정 다이어리 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getDiary(@PathVariable("diaryId") Long diaryId) {
        DiaryDto diary = diaryService.findDiary(diaryId);
        return new ResponseEntity<>(diary, HttpStatus.OK);
    }

    // 다이어리 등록
    @PostMapping
    public ResponseEntity<?> saveDiary(@RequestBody DiaryFormDto diary) {
        Diary entity = modelMapper.map(diary, Diary.class);
        Diary result = diaryService.saveDiary(entity);
        if(result == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 다이어리 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<?> modifyDiary(@PathVariable("diaryId") Long diaryId, @RequestBody DiaryFormDto diary) {
        Diary entity = modelMapper.map(diary, Diary.class);
        boolean result = diaryService.updateDiary(diaryId, entity);
        if (result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 다이어리 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> removeDiary(@PathVariable("diaryId") Long diaryId) {
        boolean result = diaryService.deleteDiary(diaryId);
        if(result) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 팔로잉 다이어리 가져오기
    @GetMapping("/follow")
    public ResponseEntity<?> getFollowDiary() {
        List<DiaryDto> diaries = diaryService.findFollowDiary();
        return new ResponseEntity<>(diaries, HttpStatus.OK);
    }
}
