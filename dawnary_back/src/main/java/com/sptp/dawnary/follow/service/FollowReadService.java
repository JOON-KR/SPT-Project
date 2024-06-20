package com.sptp.dawnary.follow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.follow.dto.response.FollowMemberResponse;
import com.sptp.dawnary.follow.repository.jpa.FollowRepository;
import com.sptp.dawnary.global.exception.MemberNotFoundException;
import com.sptp.dawnary.member.domain.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowReadService {

	private final FollowRepository followRepository;

	//팔로잉 정보 조회
	public List<FollowMemberResponse> getFollowing(Member member) {
		return followRepository.findAllByFollowing(member);
	}

	//팔로워 정보 조회
	public List<FollowMemberResponse> getFollower(Member member) {
		return followRepository.findAllByFollower(member);
	}

	//팔로우 하는지 체크
	public Optional<Follow> getFollowExist(Member myMember, Member followMember) {
		return followRepository.followCheck(myMember.getId(), followMember.getId());
	}

	public List<Member> findAllByFollowers(Member findMember) {
		return followRepository.findAllByFollowers(findMember.getId()).orElseThrow(
			MemberNotFoundException::new);
	}
}
