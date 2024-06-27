package com.sptp.dawnary.follow.domain;

import com.sptp.dawnary.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "FOLLOW",
	indexes = {@Index(name = "follower__index__member_id", columnList = "follower_member_id")
		, @Index(name = "following__index__post_id", columnList = "following_member_id")})
public class Follow {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member follower;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member following;

	public static Follow of(Member follower, Member following){
		return Follow.builder()
			.follower(follower)
			.following(following)
			.build();
	}

	@Builder
	private Follow(Member follower, Member following) {
		this.follower = follower;
		this.following = following;
	}

}

