package com.sptp.dawnary.like.service;

import com.sptp.dawnary.like.domain.Like;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.exception.AlreadyExistsLikeException;
import com.sptp.dawnary.like.repository.LikeRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    // 좋아요한 시리즈 목록 조회
    public List<Series> findSeriesByMemberId(Long memberId) {
        List<Like> likes = likeRepository.findByMemberId(memberId);
        return likes.stream()
                .map(Like::getSeries)
                .collect(Collectors.toList());
    }

    // 시리즈 좋아요
    public boolean likeSeries(Long seriesId) {
        Long memberId = 0L;
        Optional<Like> existingLike = likeRepository.findByMemberIdAndSeriesId(memberId, seriesId);
        if (existingLike.isPresent()) {
            throw new AlreadyExistsLikeException("이미 좋아요를 눌렀습니다.");
        }

        return true;
    }

}
