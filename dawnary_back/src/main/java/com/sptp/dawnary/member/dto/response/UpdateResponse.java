package com.sptp.dawnary.member.dto.response;

import com.sptp.dawnary.member.domain.Member;

import lombok.Builder;

@Builder
public record UpdateResponse(Long id, String email, String password, String name, String imagePath) {

	public static UpdateResponse create(Member member) {
		return UpdateResponse.builder()
			.id(member.getId())
			.email(member.getEmail())
			.password(member.getPassword())
			.name(member.getName())
			.imagePath(member.getImagePath())
			.build();
	}
}
