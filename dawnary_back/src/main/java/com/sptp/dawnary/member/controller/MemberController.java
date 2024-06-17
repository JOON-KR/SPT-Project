package com.sptp.dawnary.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.member.dto.LoginRequestDto;
import com.sptp.dawnary.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("login")
	public ResponseEntity<String> getMemberProfile(
		@Valid @RequestBody LoginRequestDto request
	) {
		String token = memberService.login(request);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}
}
