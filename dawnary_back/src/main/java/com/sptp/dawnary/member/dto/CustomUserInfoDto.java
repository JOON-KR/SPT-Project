package com.sptp.dawnary.member.dto;

import com.sptp.dawnary.member.domain.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto extends MemberDto {

	private Long memberId;
	private String email;
	private String password;
	private String name;
	private RoleType role;
}
