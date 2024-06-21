package com.sptp.dawnary.like.service;

import com.sptp.dawnary.global.exception.AlreadyExistsLikeException;
import com.sptp.dawnary.global.exception.LikeNotFoundException;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.SeriesNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.like.domain.Like;
import com.sptp.dawnary.like.dto.LikeRequest;
import com.sptp.dawnary.like.repository.LikeRepository;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final SeriesRepository seriesRepository;
    private final MemberRepository memberRepository;

    public List<Series> findSeriesByMemberId() {
        Long memberId = MemberInfo.getMemberId();
        return likeRepository.findByMemberId(memberId).stream()
                .map(Like::getSeries)
                .toList();
    }

    public void addLike(LikeRequest likeRequest) {
        Long memberId = MemberInfo.getMemberId();
        Long seriesId = likeRequest.seriesId();

        isExistLike(memberId, seriesId);

        Member member = getMember(memberId);
        Series series = getSeries(seriesId);

        Like like = Like.builder()
                .series(series)
                .member(member)
                .build();

        likeRepository.save(like);
    }

    public void deleteLike(Long seriesId) {
        Long memberId = MemberInfo.getMemberId();
        Member member = getMember(memberId);

        Like like = getLike(member.getId(), seriesId);

        likeRepository.delete(like);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
    }

    private Series getSeries(Long seriesId) {
        return seriesRepository.findById(seriesId)
                .orElseThrow(SeriesNotFoundException::new);
    }

    private Like getLike(Long memberId, Long seriesId) {
        return likeRepository.findByMemberIdAndSeriesId(memberId, seriesId)
                .orElseThrow(LikeNotFoundException::new);
    }

    private void isExistLike(Long memberId, Long seriesId) {
        if (likeRepository.findByMemberIdAndSeriesId(memberId, seriesId).isPresent()) {
            throw new AlreadyExistsLikeException();
        }
    }
}
