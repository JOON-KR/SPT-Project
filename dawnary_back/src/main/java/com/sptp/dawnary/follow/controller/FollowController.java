package com.sptp.dawnary.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.follow.dto.response.FollowMemberListResponse;
import com.sptp.dawnary.follow.service.FollowService;
import com.sptp.dawnary.global.util.MemberInfo;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

	private final FollowService followService;

	@PostMapping("{followingId}")
	public ResponseEntity<String> follow(@PathVariable("followingId") Long followingId) {
		log.info("FollowController");
		return followService.followMember(followingId);
	}

	@GetMapping("/following")
	public ResponseEntity<FollowMemberListResponse> myFollowingList() {
		Long memberId = MemberInfo.getMemberId();
		return followService.getFollows(memberId);
	}

	@GetMapping("/follower")
	public ResponseEntity<FollowMemberListResponse> myFollowerList() {
		Long memberId = MemberInfo.getMemberId();
		return followService.getFollowers(memberId);
	}
}
