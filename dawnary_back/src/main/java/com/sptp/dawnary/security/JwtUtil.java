package com.sptp.dawnary.security;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sptp.dawnary.member.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private final Key key;
	private final long accessTokenExpTime;

	public JwtUtil(
		@Value("${jwt.secret}") final String secretKey,
		@Value("${jwt.expiration_time}") final long accessTokenExpTime)
	{
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpTime = accessTokenExpTime;
	}

	/**
	 * Access Token 생성
	 *
	 * @param member
	 * @return Access Token String
	 */
	public String createAccessToken(MemberDto member) {
		return createToken(member, accessTokenExpTime);
	}

	/**
	 * JWT 생성
	 * @ param member
	 * @param expireTime
	 * @return JWT String
	 */
	private String createToken(MemberDto member, long expireTime) {
		Claims claims = Jwts.claims();
		claims.put("memberId", member.getId());
		claims.put("email", member.getEmail());
		claims.put("name", member.getName());
		// claims.put("role", member.getRole()); //USER, ADMIN 추가

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(Date.from(now.toInstant()))
			.setExpiration(Date.from(tokenValidity.toInstant()))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * Token에서 User ID 추출
	 *
	 * @param token
	 * @return User ID
	 */
	public Long getUserId(String token) {
		return parseClaims(token).get("memberId", Long.class);
	}

	/**
	 * JWT 검증
	 * @param token
	 * @return IsValidate
	 */
	public boolean isValidToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty", e);
		}
		return false;
	}

	/**
	 * JWT Claims 추출
	 * @param accessToken
	 * @return JWT Claims
	 */
	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
