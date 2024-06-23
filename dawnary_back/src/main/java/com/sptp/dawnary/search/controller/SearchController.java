package com.sptp.dawnary.search.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.search.dto.SearchDto;
import com.sptp.dawnary.search.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SearchController {

    private final SearchService searchService;
    
    @GetMapping("/all/{keyword}")
    public ResponseEntity<?> findAllByKeyword(@PathVariable("keyword")  String keyword) {
    	List<SearchDto> list = searchService.getSearchResult(keyword);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/auto/{keyword}")
    public ResponseEntity<?> findAutoByKeyword(@PathVariable("keyword")  String keyword) {
    	List<SearchDto> list = searchService.getAutoComplete(keyword);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
//    // 멤버 저장 API
//    @PostMapping
//    public ResponseEntity<?> saveMember(@RequestBody Member member) {
//        MemberDocument m = ss.saveMember(member);
//        return new ResponseEntity<>(m, HttpStatus.OK);
//    }
//
//    // 이름으로 멤버 검색 API
//    @GetMapping
//    public ResponseEntity<?> findMembersByName(@RequestParam String name) {
//        List<MemberDocument> list = ss.findMembersByName(name);
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//    // 모든 멤버 검색 API
//    @GetMapping("/all")
//    public ResponseEntity<?> findAllMembersByName() {
//        List<MemberDocument> list = ss.findAllMembers();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//    // 이메일로 멤버 검색 API
//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> findMembersByEmail(@PathVariable("email")  String email) {
//        List<MemberDocument> list = ss.findMembersByEmail(email);
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//    // 키워드로 멤버 검색 API (이름 또는 이메일)
//    
//    // 이름의 일부분으로 멤버 검색 API
//    //자동완성 기능은 컨트롤러에서 키워드만 받아서 member와 series 양쪽으로 보낸 뒤 두 결과를 합쳐서 resultDto를 받아오는 방향으로 설계해야겠음.
//    @GetMapping("/part/{partialName}")
//    public ResponseEntity<?> findLive(@PathVariable("partialName") String partialName) {
//    	List<SeriesDocument>list = ss.findSeriesByMemberNameOrTitleStartingWith(partialName);
//        return new ResponseEntity<>(list,  HttpStatus.OK);
//    }
//    
//    @PostMapping("/series")
//    public ResponseEntity<?> saveSeries(@RequestBody Series series) {
//        SeriesDocument s = ss.saveSeries(series);
//        return new ResponseEntity<>(s, HttpStatus.OK);
//    }
//    @GetMapping("/test/{partialName}")
//    public ResponseEntity<?> Live(@PathVariable("partialName") String partialName) {
//    	List<SeriesDocument>list = ss.findSeriesByMemberNameOrTitleStartingWith(partialName);
//        return new ResponseEntity<>(list,  HttpStatus.OK);
//    }
//    @GetMapping("/A")
//    public ResponseEntity<?> test() {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
