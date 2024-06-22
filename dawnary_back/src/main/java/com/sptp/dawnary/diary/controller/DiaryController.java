package com.sptp.dawnary.diary.controller;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.dto.DiaryResponse;
import com.sptp.dawnary.diary.dto.DiaryRequest;
import com.sptp.dawnary.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    // 모든 다이어리 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getAllDiaries(@PathVariable("memberId") Long memberId) {
        List<DiaryResponse> diaries = diaryService.findAllDiaries(memberId);
        return new ResponseEntity<>(diaries, OK);
    }

    // 특정 다이어리 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getDiary(@PathVariable("diaryId") Long diaryId) {
        DiaryResponse diary = diaryService.findDiary(diaryId);
        return new ResponseEntity<>(diary, OK);
    }

    // 다이어리 등록
    @PostMapping
    public ResponseEntity<?> saveDiary(@RequestBody DiaryRequest diaryDto) {
        Diary diary = diaryService.saveDiary(diaryDto);
        return new ResponseEntity<>(diary, CREATED);
    }

    // 다이어리 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<?> modifyDiary(@PathVariable("diaryId") Long diaryId, @RequestBody DiaryRequest diaryRequest) {
        Diary diary = diaryService.updateDiary(diaryId, diaryRequest);
        return new ResponseEntity<>(diary, OK);
    }

    // 다이어리 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> removeDiary(@PathVariable("diaryId") Long diaryId) {
        boolean result = diaryService.deleteDiary(diaryId);
        if(result) return new ResponseEntity<>(OK);
        return new ResponseEntity<>(BAD_REQUEST);
    }

    // 팔로잉 다이어리 가져오기
    @GetMapping("/follow")
    public ResponseEntity<?> getFollowDiary() {
        List<DiaryResponse> diaries = diaryService.findFollowDiary();
        return new ResponseEntity<>(diaries, OK);
    }
}
