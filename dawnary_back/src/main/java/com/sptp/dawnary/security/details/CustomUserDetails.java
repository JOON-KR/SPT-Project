package com.sptp.dawnary.security.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sptp.dawnary.member.dto.info.CustomUserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record CustomUserDetails(CustomUserInfo member) implements UserDetails {

	public CustomUserInfo getUserInfo() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> roles = new ArrayList<>();
		log.info("meber.getRole {}", member.role());
		log.info("meber.getRole.toString {}", member.role().toString());
		roles.add("ROLE_" + member.role());

		return roles.stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	@Override
	public String getPassword() {
		return member.password();
	}

	@Override
	public String getUsername() {
		return member.id().toString();
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
