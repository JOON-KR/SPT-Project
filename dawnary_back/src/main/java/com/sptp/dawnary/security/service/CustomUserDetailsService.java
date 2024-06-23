package com.sptp.dawnary.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.security.details.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Optional<Member> member = memberRepository.findByEmail(email);
		if (member.isEmpty()) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		CustomUserInfo dto = CustomUserInfo.transfer(member.get());

		return new CustomUserDetails(dto);
	}
}
