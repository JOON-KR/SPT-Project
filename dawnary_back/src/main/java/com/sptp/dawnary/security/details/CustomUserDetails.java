package com.sptp.dawnary.security.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sptp.dawnary.member.dto.CustomUserInfoDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final CustomUserInfoDto member;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> roles = new ArrayList<>();
		log.info("meber.getRole {}", member.getRole());
		log.info("meber.getRole.toString {}", member.getRole().toString());
		roles.add("ROLE_" + member.getRole());

		return roles.stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getMemberId().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
