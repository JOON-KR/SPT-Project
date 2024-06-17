package com.sptp.dawnary.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sptp.dawnary.security.service.CustomUserDetailsService;
import com.sptp.dawnary.security.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;

	/**
	 * JWT 검증 필터 수행
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request,
		final HttpServletResponse response,
		final FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		//JWT 헤더가 있을 경우
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			//JWT 유효성 검증
			if (jwtUtil.isValidToken(token)) {
				Long userId = jwtUtil.getUserId(token);

				//유저와 토큰 일치 시 userDetails 생성
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(
					userId.toString());

				if (userDetails != null) {
					//UserDetails, Password, Role -> 접근 권한 인증 Token 생성
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

					//현재 Request의 Security Context에 접근 권한 설정
					SecurityContextHolder.getContext()
						.setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		filterChain.doFilter(request, response); //다음 필터로 넘김
	}
}
