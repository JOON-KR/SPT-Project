package com.sptp.dawnary.follow.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.follow.dto.response.FollowMemberResponse;
import com.sptp.dawnary.follow.service.FollowService;
import com.sptp.dawnary.global.util.MemberInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@PostMapping("/following")
	public ResponseEntity<List<FollowMemberResponse>> myFollowingList() {
		Long memberId = MemberInfo.getMemberId();
		log.info("memberId {}", memberId);
		return followService.getFollows(memberId);
	}

	@PostMapping("/follower")
	public ResponseEntity<List<FollowMemberResponse>> myFollowerList() {
		Long memberId = MemberInfo.getMemberId();
		return followService.getFollowers(memberId);
	}
}
