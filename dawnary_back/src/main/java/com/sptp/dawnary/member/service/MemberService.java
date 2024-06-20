package com.sptp.dawnary.member.service;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sptp.dawnary.global.exception.ValidateMemberException;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.member.dto.request.LoginRequest;
import com.sptp.dawnary.member.repository.MemberRepository;
import com.sptp.dawnary.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final JwtUtil jwtUtil;
	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	@Transactional
	public String login(LoginRequest request) {
		String email = request.email();
		String password = request.password();
		log.info("request.password {}", password);
		Optional<Member> member = memberRepository.findByEmail(email);
		if (member.isEmpty()) {
			throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
		}
		log.info("member.get().getPassword {}", member.get().getPassword());
		//암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
		if (!encoder.matches(password, member.get().getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
		CustomUserInfo info = CustomUserInfo.transfer(member.get());
		log.info("CustomUserInfoDto {}", info.toString());
		return jwtUtil.createAccessToken(info);
	}

	@Transactional
	public Long signup(Member member) {
		Optional<Member> validMember = memberRepository.findByEmail(member.getEmail());

		if (validMember.isPresent()) {
			throw new ValidateMemberException("this member email is already exist. " + member.getEmail());
		}
		log.info("member.getPassword {}", member.getPassword());
		//비밀번호 해시 처리
		member.updatePassword(encoder.encode(member.getPassword()));
		log.info("member info {}", member);
		memberRepository.save(member);
		return member.getId();
	}
}

