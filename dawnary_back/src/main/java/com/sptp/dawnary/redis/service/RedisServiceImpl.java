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
	public void addRecentSearch(RedisDto redisDto) {
	    String key = RECENT_SEARCH_KEY_PREFIX + redisDto.getKey();
	    ListOperations<String, Object> listOperations = redisTemplate.opsForList();
	    
	    // 리스트의 현재 크기 확인
	    long currentSize = listOperations.size(key);
	    
	    // 리스트의 최대 크기를 5로 설정
	    int maxSize = 5;
	    
	    // 현재 리스트 크기가 최대 크기보다 큰 경우, 가장 오래된 요소를 삭제
	    if (currentSize >= maxSize) {
	        listOperations.rightPop(key);  // 오른쪽 끝(가장 오래된 요소)을 삭제
	    }
	    
	    // 새 요소를 리스트의 왼쪽에 추가
	    listOperations.leftPush(key, redisDto.getValue());
	}

	@Override
	public List<Object> getRecentSearches(RedisDto redisDto) {
		String key = RECENT_SEARCH_KEY_PREFIX + redisDto.getKey();
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, 0, -1);
	}
}
