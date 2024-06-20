package com.sptp.dawnary.like.service;

import com.sptp.dawnary.global.exception.AlreadyExistsLikeException;
import com.sptp.dawnary.global.exception.LikeNotFoundException;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.exception.SeriesNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.like.domain.Like;
import com.sptp.dawnary.like.repository.LikeRepository;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.series.domain.Series;
import com.sptp.dawnary.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final SeriesRepository seriesRepository;
    private final MemberRepository memberRepository;

    // 좋아요한 시리즈 목록 조회
    public List<Series> findSeriesByMemberId() {
        Long memberId = MemberInfo.getMemberId();
        List<Like> likes = likeRepository.findByMemberId(memberId);
        return likes.stream()
                .map(Like::getSeries)
                .toList();
    }

    // 시리즈 좋아요
    public boolean addLike(Long seriesId) {
        Long memberId = MemberInfo.getMemberId();
        Optional<Like> existingLike = likeRepository.findByMemberIdAndSeriesId(memberId, seriesId);

        if (existingLike.isPresent()) {
            throw new AlreadyExistsLikeException("이미 좋아요를 눌렀습니다.");
        }

        Member member = getMember();
        Optional<Series> series = seriesRepository.findById(seriesId);
        if(series.isEmpty()) {
            throw new SeriesNotFoundException("존재하지 않는 시리즈입니다.");
        }

        Like like = Like.builder()
                .series(series.get())
                .member(member)
                .build();

        likeRepository.save(like);

        return true;
    }

    // 시리즈 삭제
    public boolean deleteLike(Long seriesId) {
        Member member = getMember();
        Optional<Series> series = seriesRepository.findById(seriesId);

        if(series.isEmpty()) {
            throw new SeriesNotFoundException("존재하지 않는 시리즈입니다.");
        }

        Optional<Like> like = likeRepository.findByMemberIdAndSeriesId(member.getId(), seriesId);

        if(like.isEmpty()) {
            throw new LikeNotFoundException("존재하지 않는 좋아요입니다.");
        }

        likeRepository.delete(like.get());

        return true;

    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) throw new MemberNotFoundException("멤버가 존재하지 않습니다.");
        return member.get();
    }

}
