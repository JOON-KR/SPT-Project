package com.sptp.dawnary.search.service;


import java.util.List;

import com.sptp.dawnary.elastic.document.MemberDocument;
import com.sptp.dawnary.elastic.document.SeriesDocument;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.series.domain.Series;

public interface SearchService {

	//후에 멤버 서비스에서 저장하기, 지금은 테스트
	MemberDocument saveMember(Member member);

	// 모든 멤버 조회
	List<MemberDocument> findAllMembers();

	// 이름으로 멤버 검색
	List<MemberDocument> findMembersByName(String name);

	// 이메일으로 멤버 검색
	List<MemberDocument> findMembersByEmail(String email);

	// 이름으로 멤버 검색
	List<MemberDocument> findMembersByNameAndEmail(String keyword);
	
	//실시간 검색 (멤버)
	List<MemberDocument> findMembersByNameOrEmailStartingWith(String keyword);
	//후에 시리즈 서비스에서 저장, 지금은테스트
	SeriesDocument saveSeries(Series series);

	//모든 시리즈 조회
	List<SeriesDocument> findAllSeries(); 
	
	//공개 시리즈만 조회
	List<SeriesDocument> findSeriesByStatus(int status); 
	

    // 멤버 이름과 제목에서 자동 완성 기능을 위한 메서드
    List<SeriesDocument> findSeriesByMemberNameOrTitleStartingWith(String keyword);

    // 키워드로 시리즈 검색
    List<SeriesDocument> findSeriesByKeyword(String keyword);
}