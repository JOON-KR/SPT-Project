package com.sptp.dawnary.follow.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sptp.dawnary.follow.domain.Follow;
import com.sptp.dawnary.follow.repository.querydsl.FollowRepositoryCustom;
import com.sptp.dawnary.member.domain.Member;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {
	@Query("select f from Follow f where f.follower.id = :memberId and f.following.id = :followerId")
	Optional<Follow> followCheck(@Param("memberId") Long memberId, @Param("followerId") Long followerId);

	@Query("select m from Member m where m.id in ( select f.follower.id from Follow f where f.following.id = :memberId) ")
	Optional<List<Member>> findAllByFollowers(@Param("memberId") Long memberId);
}
