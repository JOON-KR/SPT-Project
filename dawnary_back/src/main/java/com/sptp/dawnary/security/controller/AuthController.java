package com.sptp.dawnary.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.redis.service.RedisService;
import com.sptp.dawnary.security.details.CustomUserDetails;
import com.sptp.dawnary.security.service.CustomUserDetailsService;
import com.sptp.dawnary.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

	private final JwtUtil jwtUtil;
	private final RedisService redisService;
	private final CustomUserDetailsService customUserDetailsService;

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAccessToken(@RequestBody String refreshToken) {
		try {
			// Refresh Token 검증
			if (jwtUtil.isValidToken(refreshToken)) {
				String email = jwtUtil.getUsernameFromToken(refreshToken);
				String storedRefreshToken = redisService.getRefreshToken(email);

				if (refreshToken.equals(storedRefreshToken)) {
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

					if (userDetails != null) {
						CustomUserInfo userInfo = ((CustomUserDetails) userDetails).getUserInfo();
						String newAccessToken = jwtUtil.createAccessToken(userInfo);
						return ResponseEntity.ok(newAccessToken);
					} else {
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
					}
				} else {
					log.error("Stored refresh token does not match provided token.");
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
				}
			} else {
				log.error("Invalid refresh token");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
			}
		} catch (Exception e) {
			log.error("Error processing refresh token: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing refresh token");
		}
	}
}
