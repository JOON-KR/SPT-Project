package com.sptp.dawnary.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.member.dto.request.LoginRequest;
import com.sptp.dawnary.member.dto.request.LogoutRequest;
import com.sptp.dawnary.member.dto.request.MemberRequest;
import com.sptp.dawnary.member.dto.request.UpdateRequest;
import com.sptp.dawnary.member.dto.response.EmailListResponse;
import com.sptp.dawnary.member.dto.response.UpdateResponse;
import com.sptp.dawnary.member.service.MemberService;
import com.sptp.dawnary.redis.service.RedisService;
import com.sptp.dawnary.security.dto.TokenResponse;
import com.sptp.dawnary.security.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final RedisService redisService;
	private final JwtUtil jwtUtil;

	@PostMapping("login")
	public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
		String accessToken = memberService.login(request);
		CustomUserInfo userInfo = memberService.getUserInfo(request.email());
		String refreshToken = jwtUtil.createRefreshToken(userInfo);
		redisService.saveRefreshToken(request.email(), refreshToken);
		return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(accessToken, refreshToken));
	}

	@PostMapping("/logout")
	public void logout(@RequestBody LogoutRequest request) {
		redisService.deleteRefreshToken(request.email());
	}

	@PostMapping("signup")
	public ResponseEntity<Long> signup(@Valid @RequestBody MemberRequest member) {
		log.info("member info {}", member);
		Member entity = Member.transfer(member);
		log.info("after ModelMapper info {}", entity.toString());
		Long id = memberService.signup(entity);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}

	@PutMapping("update")
	public ResponseEntity<UpdateResponse> update(@Valid @RequestBody UpdateRequest request) {
		Member updateMember = memberService.update(request);
		return ResponseEntity.status(HttpStatus.OK).body(UpdateResponse.create(updateMember));
	}

	@DeleteMapping("delete")
	public ResponseEntity<String> delete() {
		memberService.delete();
		return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
	}

	@GetMapping("all")
	public ResponseEntity<EmailListResponse> getAllEmails() {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.getEmails());
	}

	@PostMapping("refresh")
	public ResponseEntity<TokenResponse> refresh(@RequestBody String refreshToken) {
		if (!jwtUtil.isValidToken(refreshToken)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String email = jwtUtil.getUsernameFromToken(refreshToken);
		Member member = memberService.getMemberByEmail(email).orElseThrow(() -> new UsernameNotFoundException("멤버가 존재하지 않습니다."));
		CustomUserInfo info = CustomUserInfo.transfer(member);
		String newAccessToken = jwtUtil.createAccessToken(info);
		return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(newAccessToken, refreshToken));
	}
}
