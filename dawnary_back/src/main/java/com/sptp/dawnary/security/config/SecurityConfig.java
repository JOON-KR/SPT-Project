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

import com.sptp.dawnary.redis.service.RedisService;
import com.sptp.dawnary.security.entrypoint.CustomAuthenticationEntryPoint;
import com.sptp.dawnary.security.filter.JwtAuthFilter;
import com.sptp.dawnary.security.handler.CustomAccessDeniedHandler;
import com.sptp.dawnary.security.service.CustomUserDetailsService;
import com.sptp.dawnary.security.util.JwtUtil;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtUtil jwtUtil;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final RedisService redisService;

	private static final String[] AUTH_WHITELIST = {"/member/login", "/member/signup",
		"/swagger-ui/**", "/api-docs", "swagger-ui-custom.html"};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(Customizer.withDefaults());

		http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.formLogin(AbstractHttpConfigurer::disable);
		http.httpBasic(AbstractHttpConfigurer::disable);

		// JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
		http.addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtil, redisService),
			UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));

		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers(AUTH_WHITELIST).permitAll()
			.anyRequest().authenticated()
		);

		return http.build();
	}
}
