package com.sptp.dawnary.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sptp.dawnary.security.entrypoint.CustomAuthenticationEntryPoint;
import com.sptp.dawnary.security.filter.JwtAuthFilter;
import com.sptp.dawnary.security.handler.CustomAccessDeniedHandler;
import com.sptp.dawnary.security.service.CustomUserDetailsService;
import com.sptp.dawnary.security.util.JwtUtil;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
//메서드 수준에서의 보안 처리 활성화
//@Secure, @PreAuthorize 어노테이션 사용 가능
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	private static final String[] AUTH_WHITELIST = {"/member/login", "/member/signup",
		"/swagger-ui/**", "/api-docs", "swagger-ui-custom.html"};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//CSRF, CORS
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors((Customizer.withDefaults()));

		//세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 x
		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
			SessionCreationPolicy.STATELESS));

		//FormLogin, BasicHttp 비활성화
		http.formLogin(AbstractHttpConfigurer::disable);
		http.httpBasic(AbstractHttpConfigurer::disable);

		//JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
		http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtil),
			UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(
			authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));

		//권한 규칙 작성
		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers(AUTH_WHITELIST).permitAll()
			//@PreAuthorization 사용 -> 모든 경로에 대한 인증처리는 Pass
			.anyRequest().permitAll()
		);

		return http.build();
	}
}
