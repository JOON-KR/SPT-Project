package com.sptp.dawnary.member.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.exception.ValidateMemberException;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.CustomUserInfoDto;
import com.sptp.dawnary.member.dto.LoginRequestDto;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;
	private final ModelMapper modelMapper;

	@Transactional
	public String login(LoginRequestDto dto) {
		String email = dto.getEmail();
		String password = dto.getPassword();
		Optional<Member> member = memberRepository.findMemberByEmail(email);
		if (member.isEmpty()) {
			throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
		}

		//암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
		if (!encoder.matches(password, member.get().getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		CustomUserInfoDto info = modelMapper.map(member, CustomUserInfoDto.class);
		return jwtUtil.createAccessToken(info);
	}

	@Transactional
	public Long signup(Member member) {
		Optional<Member> validMember = memberRepository.findMemberByEmail(member.getEmail());

		if (validMember.isPresent()) {
			throw new ValidateMemberException("this member email is already exist. " + member.getEmail());
		}
		//비밀번호 해시 처리
		member.updatePassword(encoder.encode(member.getPassword()));
		memberRepository.create(member);
		return member.getId();
	}
}
