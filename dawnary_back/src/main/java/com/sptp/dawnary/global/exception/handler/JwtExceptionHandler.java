package com.sptp.dawnary.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.member.service.MemberService;
import com.sptp.dawnary.redis.service.RedisService;
import com.sptp.dawnary.security.dto.TokenResponse;
import com.sptp.dawnary.security.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class JwtExceptionHandler {

	private final JwtUtil jwtUtil;
	private final RedisService redisService;
	private final MemberService memberService;

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<TokenResponse> handleExpiredJwtException(ExpiredJwtException ex) {
		String refreshToken = ex.getClaims().get("refreshToken", String.class);
		if (jwtUtil.isValidToken(refreshToken) && refreshToken.equals(redisService.getRefreshToken(jwtUtil.getUsernameFromToken(refreshToken)))) {
			String email = jwtUtil.getUsernameFromToken(refreshToken);
			Member member = memberService.getMemberByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
			CustomUserInfo userInfo = CustomUserInfo.transfer(member);
			String newAccessToken = jwtUtil.createAccessToken(userInfo);
			return ResponseEntity.ok(new TokenResponse(newAccessToken, refreshToken));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}
}
