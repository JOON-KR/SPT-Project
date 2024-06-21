package com.sptp.dawnary.search.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.elastic.document.MemberDocument;
import com.sptp.dawnary.elastic.document.SeriesDocument;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.search.service.SearchService;
import com.sptp.dawnary.series.domain.Series;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SearchController {

    private final SearchService searchService;
    
    // 이름으로 멤버 검색 API
    @GetMapping
    public ResponseEntity<?> findMembersByName(@RequestParam String name) {
        List<MemberDocument> list = searchService.findMembersByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 모든 멤버 검색 API
    @GetMapping("/all")
    public ResponseEntity<?> findAllMembersByName() {
        List<MemberDocument> list = searchService.findAllMembers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 이메일로 멤버 검색 API
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findMembersByEmail(@PathVariable("email")  String email) {
        List<MemberDocument> list = searchService.findMembersByEmail(email);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 키워드로 멤버 검색 API (이름 또는 이메일)
    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<?> findMembersByKeyword(@PathVariable("keyword")  String keyword) {
        List<MemberDocument> list = searchService.findMembersByNameAndEmail(keyword);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    // 이름의 일부분으로 멤버 검색 API
    //자동완성 기능은 컨트롤러에서 키워드만 받아서 member와 series 양쪽으로 보낸 뒤 두 결과를 합쳐서 resultDto를 받아오는 방향으로 설계해야겠음.
    @GetMapping("/part/{partialName}")
    public ResponseEntity<?> findLive(@PathVariable("partialName") String partialName) {
    	List<SeriesDocument>list = searchService.findSeriesByMemberNameOrTitleStartingWith(partialName);
        return new ResponseEntity<>(list,  HttpStatus.OK);
    }
    @GetMapping("/test/{partialName}")
    public ResponseEntity<?> Live(@PathVariable("partialName") String partialName) {
    	List<SeriesDocument>list = searchService.findSeriesByMemberNameOrTitleStartingWith(partialName);
        return new ResponseEntity<>(list,  HttpStatus.OK);
    }
}
