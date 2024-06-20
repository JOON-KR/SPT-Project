package com.sptp.dawnary.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.follow.service.FollowReadService;
import com.sptp.dawnary.follow.service.FollowService;
import com.sptp.dawnary.follow.service.FollowWriteService;
import com.sptp.dawnary.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

	private final MemberService memberService;
	private final FollowService followService;
	private final FollowReadService followReadService;
	private final FollowWriteService followWriteService;

	@PostMapping("{memberId}")
	public ResponseEntity<String> follow(@PathVariable("memberId") Long followingId) {
		return followService.followMember(followingId);
	}
}
