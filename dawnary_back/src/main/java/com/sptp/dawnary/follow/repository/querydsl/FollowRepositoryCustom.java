package com.sptp.dawnary.follow.repository.querydsl;

import java.util.List;

import com.sptp.dawnary.follow.dto.response.FollowMemberResponse;
import com.sptp.dawnary.member.domain.Member;


public interface FollowRepositoryCustom {
	List<FollowMemberResponse> findAllByFollowing(Member member);
	List<FollowMemberResponse> findAllByFollower(Member member);
}
