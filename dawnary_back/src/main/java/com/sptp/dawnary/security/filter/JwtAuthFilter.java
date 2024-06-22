package com.sptp.dawnary.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sptp.dawnary.member.dto.info.CustomUserInfo;
import com.sptp.dawnary.security.details.CustomUserDetails;
import com.sptp.dawnary.security.service.CustomUserDetailsService;
import com.sptp.dawnary.security.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			try {
				if (jwtUtil.isValidToken(token)) {
					String email = jwtUtil.getUsernameFromToken(token);

					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

					if (userDetails != null) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch (ExpiredJwtException e) {
				// 이곳에서 만료된 토큰 예외를 처리
				String refreshToken = request.getHeader("RefreshToken");
				if (refreshToken != null && jwtUtil.isValidToken(refreshToken)) {
					String email = jwtUtil.getUsernameFromToken(refreshToken);
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
					if (userDetails != null) {
						CustomUserInfo userInfo = ((CustomUserDetails) userDetails).getUserInfo();
						String newAccessToken = jwtUtil.createAccessToken(userInfo);
						response.setHeader("Authorization", "Bearer " + newAccessToken);
					}
				} else {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
