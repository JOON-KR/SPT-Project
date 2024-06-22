package com.sptp.dawnary.redis.service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.sptp.dawnary.redis.dto.RedisDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

	private static final String RECENT_SEARCH_KEY_PREFIX = "recent_search:";
	private final RedisTemplate<String, Object> redisTemplate;

	@Value("${jwt.refresh_expiration_time}")
	private long refreshTokenExpTime;

	@Override
	public void saveRefreshToken(String email, String refreshToken) {
		log.info("Saving refresh token for email: {}", email);
		redisTemplate.opsForValue().set(email, refreshToken, refreshTokenExpTime, TimeUnit.SECONDS);
	}

	@Override
	public String getRefreshToken(String email) {
		log.info("Retrieving refresh token for email: {}", email);
		return (String) redisTemplate.opsForValue().get(email);
	}

	@Override
	public void deleteRefreshToken(String email) {
		log.info("Deleting refresh token for email: {}", email);
		redisTemplate.delete(email);
	}

	@Override
	public void setValue(RedisDto redisDto) {
		String key = redisDto.getKey();
		Object value = redisDto.getValue();
		log.info("Setting value for key: {}", key);
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void setValueWithTimeout(RedisDto redisDto) {
		String key = redisDto.getKey();
		Object value = redisDto.getValue();
		Duration timeout = redisDto.getDuration();
		log.info("Setting value with timeout for key: {}", key);
		redisTemplate.opsForValue().set(key, value, timeout);
	}

	@Override
	public String getValue(RedisDto redisDto) {
		String key = redisDto.getKey();
		log.info("Getting value for key: {}", key);
		Object value = redisTemplate.opsForValue().get(key);
		return value != null ? value.toString() : null;
	}

	@Override
	public void deleteValue(RedisDto redisDto) {
		String key = redisDto.getKey();
		log.info("Deleting value for key: {}", key);
		redisTemplate.delete(key);
	}

	@Override
	public void addTokenToBlacklist(final String token, final long expirationTime) {
		redisTemplate.opsForValue().set(token, "blacklisted", expirationTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean isTokenBlacklisted(final String token) {
		return redisTemplate.hasKey(token);
	}

	@Override
	public void addRecentSearch(Long id, String keyword) {
		String key = RECENT_SEARCH_KEY_PREFIX + id;
		log.info("Adding recent search for ID: {} with keyword: {}", id, keyword);
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		listOperations.leftPush(key, keyword);
	}

	@Override
	public List<Object> getRecentSearches(Long id) {
		String key = RECENT_SEARCH_KEY_PREFIX + id;
		log.info("Retrieving recent searches for ID: {}", id);
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, 0, -1);
	}
}
