package com.sptp.dawnary.member.dto.info;

import java.io.Serializable;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.domain.RoleType;

import lombok.Builder;

@Builder
public record CustomUserInfo(Long id, String email, String password, String name, RoleType role) implements
	Serializable {

	public static CustomUserInfo transfer(Member member) {
		return CustomUserInfo.builder()
			.id(member.getId())
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.role(member.getRole())
			.build();
	}
}
