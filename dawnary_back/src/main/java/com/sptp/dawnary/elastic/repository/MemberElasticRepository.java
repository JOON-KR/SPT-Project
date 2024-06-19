package com.sptp.dawnary.elastic.repository;

import com.sptp.dawnary.elastic.document.MemberDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberElasticRepository extends ElasticsearchRepository<MemberDocument, Long> {

    // 이름으로 멤버 검색
    @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}]}}")
    List<MemberDocument> findByName(String name);

    // 모든 문서 조회
    List<MemberDocument> findAll();

    // 이메일로 멤버 검색
    @Query("{\"bool\": {\"must\": [{\"match\": {\"email\": \"?0\"}}]}}")
    List<MemberDocument> findByEmail(String email);

    // 이름과 이메일로 멤버 검색
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"email\": \"?0\"}}]}}")
    List<MemberDocument> findByNameOrEmail(String keyword);
    
    // 이름의 일부분으로 검색하는 쿼리 추가
    @Query("{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"and\"}}}")
    List<MemberDocument> findByPartialName(String partialName);

}
