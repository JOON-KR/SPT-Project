package com.sptp.dawnary.follow.domain;

import static jakarta.persistence.FetchType.*;

import com.sptp.dawnary.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FOLLOW")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

	@Id
	@GeneratedValue
	@Column(name = "follow_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "follower_id")
	private Member follower;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "following_id")
	private Member following;

	public Follow(Member follower, Member following) {
		this.follower = follower;
		this.following = following;
	}
}
