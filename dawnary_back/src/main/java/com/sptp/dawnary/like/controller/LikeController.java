package com.sptp.dawnary.like.controller;

import com.sptp.dawnary.like.dto.LikeRequest;
import com.sptp.dawnary.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<?> getAllLikes() {
        return new ResponseEntity<>(likeService.findSeriesByMemberId(), OK);
    }

    @PostMapping
    public ResponseEntity<?> addLikes(@RequestBody LikeRequest likeRequest) {
        likeService.addLike(likeRequest);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{seriesId}")
    public ResponseEntity<?> removeLikes(@PathVariable("seriesId") Long seriesId) {
        likeService.deleteLike(seriesId);
        return new ResponseEntity<>(OK);
    }
}