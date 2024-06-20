package com.sptp.dawnary.follow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.follow.dto.response.FollowMemberResponse;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.global.util.MemberInfo;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final FollowReadService followReadService;
	private final FollowWriteService followWriteService;

	public ResponseEntity<String> followMember(Long followingId) {
		log.info("FollowService - followMember");
		log.info("followingId {}", followingId);
		Long memberId = MemberInfo.getMemberId();
		log.info("memberId {}", memberId);
		Optional<Member> loginMember = memberRepository.findById(memberId);
		if(loginMember.isEmpty()) {
			throw new MemberNotFoundException();
		}
		log.info("loginMember {}", loginMember.get());
		memberService.sameUserCheck(loginMember.get().getId(), followingId);
		Optional<Member> followMember = memberService.getMember(followingId);
		if(followMember.isEmpty()) {
			throw new MemberNotFoundException();
		}
		Optional<Follow> follow = followReadService.getFollowExist(loginMember.get(),
			followMember.get());
		follow.ifPresentOrElse(followWriteService::unfollow,
			() -> followWriteService.follow(Follow.of(loginMember.orElse(null), followMember.get())));

		return ResponseEntity.ok("성공");
	}

	public ResponseEntity<List<FollowMemberResponse>> getFollows(Long memberId) {
		Optional<Member> member = memberService.getMember(memberId);
		if(member.isEmpty()) {
			throw new MemberNotFoundException();
		}
		return ResponseEntity.ok().body(followReadService.getFollowing(member.get()));
	}


	public ResponseEntity<List<FollowMemberResponse>> getFollowers(Long memberId) {
		Optional<Member> member = memberService.getMember(memberId);
		if(member.isEmpty()) {
			throw new MemberNotFoundException();
		}
		return ResponseEntity.ok().body(followReadService.getFollower(member.get()));
	}



}
