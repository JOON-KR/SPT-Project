package com.sptp.dawnary.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.sptp.dawnary.elastic.document.SeriesDocument;

@Repository
public interface SeriesElasticRepository extends ElasticsearchRepository<SeriesDocument, Long> {
    // 제목으로 시리즈 검색
    List<SeriesDocument> findByTitle(String title);

    // 상태로 시리즈 검색
    List<SeriesDocument> findByStatus(int status);

    List<SeriesDocument> findAll();

    // 자동 완성 기능을 위한 메서드
    List<SeriesDocument> findByMemberNameStartingWithOrTitleStartingWith(String memberNameKeyword, String titleKeyword);

    // 키워드로 시리즈 검색
    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"memberName\": \"?0\"}}]}}")
    List<SeriesDocument> findByKeyword(String keyword);
    
    // 제목 또는 작성자에서 키워드를 포함하는 시리즈 검색
    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"memberName\": \"?0\"}}]}}")
    List<SeriesDocument> findByTitleOrMemberNameStartingWith(String keyword);

}
