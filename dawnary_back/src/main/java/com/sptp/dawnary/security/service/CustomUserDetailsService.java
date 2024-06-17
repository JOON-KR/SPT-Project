package com.sptp.dawnary.security.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.CustomUserInfoDto;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.security.details.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final ModelMapper mapper;

	@Override
	public UserDetails loadUserByUsername(final String id) throws UsernameNotFoundException {
		Member member = memberRepository.findMemberById(Long.parseLong(id));
		if (member == null) {
			throw new UsernameNotFoundException("해당하는 유저가 없습니다.");
		}

		CustomUserInfoDto dto = mapper.map(member, CustomUserInfoDto.class);

		return new CustomUserDetails(dto);
	}
}
