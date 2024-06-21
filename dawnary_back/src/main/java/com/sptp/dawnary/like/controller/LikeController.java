package com.sptp.dawnary.like.controller;

import com.sptp.dawnary.like.dto.LikeFormDto;
import com.sptp.dawnary.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    // 모든 좋아요 조회
    @GetMapping
    public ResponseEntity<?> getAllLikes() {
        return new ResponseEntity<>(likeService.findSeriesByMemberId(), HttpStatus.OK);
    }

    // 좋아요 등록
    @PostMapping()
    public ResponseEntity<?> addLikes(@RequestBody LikeFormDto likeFormDto) {
        likeService.addLike(likeFormDto.getSeriesId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 좋아요 삭제
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<?> removeLikes(@PathVariable("seriesId") Long seriesId) {
        likeService.deleteLike(seriesId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
