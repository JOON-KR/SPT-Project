package com.sptp.dawnary.search.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.elastic.document.MemberDocument;
import com.sptp.dawnary.elastic.document.SeriesDocument;
import com.sptp.dawnary.elastic.repository.MemberElasticRepository;
import com.sptp.dawnary.elastic.repository.SeriesElasticRepository;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.redis.dto.RedisDto;
import com.sptp.dawnary.redis.service.RedisService;
import com.sptp.dawnary.search.dto.SearchDto;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final MemberElasticRepository memberElasticRepository;
    private final SeriesElasticRepository seriesElasticRepository;
    private final RedisService redisService;
	@Override
	public List<SearchDto> getAutoComplete(String keyword) {
		List<SeriesDocument> seriesList = seriesElasticRepository.findByMemberNameStartingWithOrTitleStartingWith(keyword, keyword);
		List<MemberDocument> memberList =  memberElasticRepository.findByNameAutocomplete( keyword);
		List<SearchDto>result = new ArrayList<>();
		for(MemberDocument member : memberList) {
			result.add(SearchDto.builder()
					.type("member")
					.id(member.getId())
					.name(member.getName())
					.imagePath(member.getImagePath())
					.email(member.getEmail()).build());
		}
		for(SeriesDocument series : seriesList) {
			result.add(SearchDto.builder()
					.type("series")
					.id(series.getId())
					.name(series.getMemberName())
					.title(series.getTitle())
					.imagePath(series.getImagePath()).build());
		}
		return result;
	}
	@Override
	public List<SearchDto> getSearchResult(String keyword) {
		List<SeriesDocument> seriesList = seriesElasticRepository.findByKeyword(keyword);
		List<MemberDocument> memberList =  memberElasticRepository.findByNameOrEmail(keyword);
		RedisDto redisDto = RedisDto.builder()
				.key(MemberInfo.getMemberId()+"")
				.value(keyword)
				.build();
		redisService.addRecentSearch(redisDto);
		List<SearchDto>result = new ArrayList<>();
		for(MemberDocument member : memberList) {
			result.add(SearchDto.builder()
					.type("member")
					.id(member.getId())
					.name(member.getName())
					.imagePath(member.getImagePath())
					.email(member.getEmail()).build());
		}
		for(SeriesDocument series : seriesList) {
			result.add(SearchDto.builder()
					.type("series")
					.id(series.getId())
					.name(series.getMemberName())
					.title(series.getTitle())
					.imagePath(series.getImagePath()).build());
		}
		return result;
	}
	@Override
	public List<Object> getRecentSearch(RedisDto redisDto) {
		List<Object> result = redisService.getRecentSearches(redisDto);
		return null;
	}

    // 멤버 관련 메서드들

//    @Override
//    public MemberDocument saveMember(Member member) {
//        MemberDocument md = MemberDocument.from(member);
//        return memberElasticRepository.save(md);
//    }
//
//    @Override
//    public List<MemberDocument> findAllMembers() {
//        return memberElasticRepository.findAll();
//    }
//
//    @Override
//    public List<MemberDocument> findMembersByName(String name) {
//        return memberElasticRepository.findByName(name);
//    }
//
//    @Override
//    public List<MemberDocument> findMembersByEmail(String email) {
//        return memberElasticRepository.findByEmail(email);
//    }
//
//    @Override
//    public List<MemberDocument> findMembersByNameAndEmail(String keyword) {
//        return memberElasticRepository.findByNameOrEmail(keyword);
//    }
//
//
//    // 시리즈 관련 메서드들
//
//    @Override
//    public SeriesDocument saveSeries(Series series) {
//        SeriesDocument sd = SeriesDocument.from(series);
//        return seriesElasticRepository.save(sd);
//    }
//
//    @Override
//    public List<SeriesDocument> findAllSeries() {
//        return seriesElasticRepository.findAll();
//    }
//
//    @Override
//    public List<SeriesDocument> findSeriesByStatus(int status) {
//        return seriesElasticRepository.findByStatus(status);
//    }
//
//    @Override
//    public List<SeriesDocument> findSeriesByKeyword(String keyword) {
//        return seriesElasticRepository.findByKeyword(keyword);
//    }
//    
//    @Override
//    public List<SeriesDocument> findSeriesByMemberNameOrTitleStartingWith(String keyword) {
//        return seriesElasticRepository.findByMemberNameStartingWithOrTitleStartingWith(keyword, keyword);
//    }
//
//    @Override
//    public List<MemberDocument> findMembersByNameOrEmailStartingWith(String keyword) {
//        return memberElasticRepository.findByNameAutocomplete( keyword);
//    }
    

}
