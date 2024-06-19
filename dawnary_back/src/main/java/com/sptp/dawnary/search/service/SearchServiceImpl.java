package com.sptp.dawnary.search.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.elastic.document.MemberDocument;
import com.sptp.dawnary.elastic.document.SeriesDocument;
import com.sptp.dawnary.elastic.repository.MemberElasticRepository;
import com.sptp.dawnary.elastic.repository.SeriesElasticRepository;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.series.domain.Series;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final MemberElasticRepository memberElasticRepository;
    private final SeriesElasticRepository seriesElasticRepository;

    // 멤버 관련 메서드들

    @Override
    public MemberDocument saveMember(Member member) {
        MemberDocument md = MemberDocument.from(member);
        return memberElasticRepository.save(md);
    }

    @Override
    public List<MemberDocument> findAllMembers() {
        return memberElasticRepository.findAll();
    }

    @Override
    public List<MemberDocument> findMembersByName(String name) {
        return memberElasticRepository.findByName(name);
    }

    @Override
    public List<MemberDocument> findMembersByEmail(String email) {
        return memberElasticRepository.findByEmail(email);
    }

    @Override
    public List<MemberDocument> findMembersByNameAndEmail(String keyword) {
        return memberElasticRepository.findByNameOrEmail(keyword);
    }


    // 시리즈 관련 메서드들

    @Override
    public SeriesDocument saveSeries(Series series) {
        SeriesDocument sd = SeriesDocument.from(series);
        return seriesElasticRepository.save(sd);
    }

    @Override
    public List<SeriesDocument> findAllSeries() {
        return seriesElasticRepository.findAll();
    }

    @Override
    public List<SeriesDocument> findSeriesByStatus(int status) {
        return seriesElasticRepository.findByStatus(status);
    }

    @Override
    public List<SeriesDocument> findSeriesByKeyword(String keyword) {
        return seriesElasticRepository.findByKeyword(keyword);
    }
    
    @Override
    public List<SeriesDocument> findSeriesByMemberNameOrTitleStartingWith(String keyword) {
        return seriesElasticRepository.findByMemberNameStartingWithOrTitleStartingWith(keyword, keyword);
    }

}
