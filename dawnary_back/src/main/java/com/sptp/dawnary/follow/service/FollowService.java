package com.sptp.dawnary.follow.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

	private final MemberService memberService;
	private final FollowReadService followReadService;
	private final FollowWriteService followWriteService;

	public ResponseEntity<String> followMember(Long followingId) {
		Member loginMember = memberService.getMember();
		log.info("loginMember {}", loginMember);
		memberService.sameUserCheck(loginMember.getId(), followingId);
		Optional<Member> followMember = memberService.getMember(followingId);
		if(followMember.isEmpty()) {
			throw new MemberNotFoundException();
		}
		Optional<Follow> follow = followReadService.getFollowExist(loginMember,
			followMember.get());
		follow.ifPresentOrElse(followWriteService::unfollow,
			() -> followWriteService.follow(Follow.of(loginMember, followMember.get())));

		return ResponseEntity.ok("성공");
	}
}
