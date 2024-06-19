package com.sptp.dawnary.series.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.series.repository.SeriesRepository;
import com.sptp.dawnary.series.domain.Series;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final MemberRepository memberRepository;

    // 전체 시리즈 조회
    public List<Series> findAllSeriesByLatest() {
        return seriesRepository.findAllOrderByLatest();
    }

    // 명예의 전당(좋아요 많은 순 조회)
    public List<Series> findAllSeriesByLikes() {
        return seriesRepository.findAllOrderByLatest();
    }

    // 월별 시리즈
    public List<Series> findMonthlySeriesByLikes() {
        YearMonth currentMonth = YearMonth.from(LocalDateTime.now());
        LocalDateTime startDate = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        return seriesRepository.findAllOrderByLikesByMonth(startDate, endDate);
    }

    // 멤버 시리즈 최신순
    public List<Series> findMemberSeriesByLatest(Long memberId) {
        return seriesRepository.findMemberSeriesByLatest(memberId);
    }

    // 멤버 시리즈 좋아요순
    public List<Series> findMemberSeriesByLikes(Long memberId) {
        return seriesRepository.findMemberSeriesByLikes(memberId);
    }

    // 시리즈 저장
    public Series saveSeries(Series series) {
        series.setMember(getMember());
        Series result = seriesRepository.save(series);
        return result;
    }

    // 시리즈 수정
    public boolean updateSeries(Long seriesId, Series series) {
        series.setMember(getMember());
        if(seriesRepository.existsById(seriesId)) {
            series.setId(seriesId);
            seriesRepository.save(series);
            return true;
        }
        return false;
    }

    // 시리즈 삭제
    public boolean deleteSeries(Long seriesId) {
        if(seriesRepository.existsById(seriesId)) {
            seriesRepository.deleteById(seriesId);
            return true;
        }

        return false;
    }

    private Member getMember() {
        Long memberId = MemberInfo.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) throw new MemberNotFoundException("멤버가 존재하지 않습니다.");
        return member.get();
    }
}
