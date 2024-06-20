package com.sptp.dawnary.member.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.request.LoginRequest;
import com.sptp.dawnary.member.dto.request.MemberRequest;
import com.sptp.dawnary.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final ModelMapper modelMapper;

	@PostMapping("login")
	public ResponseEntity<String> getMemberProfile(
		@Valid @RequestBody LoginRequest request
	) {
		String token = memberService.login(request);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("signup")
	public ResponseEntity<Long> signup(@Valid @RequestBody MemberRequest member) {
		log.info("member info {}", member);
		Member entity = Member.transfer(member);
		log.info("after ModelMapper info {}", entity.toString());
		Long id = memberService.signup(entity);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
}
