package com.sptp.dawnary.redis.service;

import java.util.List;

import com.sptp.dawnary.redis.dto.RedisDto;

public interface RedisService {
	void saveRefreshToken(String username, String refreshToken);

	String getRefreshToken(String username);

	void deleteRefreshToken(String username);

	void setValue(RedisDto redisDto);

	void setValueWithTimeout(RedisDto redisDto);

	String getValue(RedisDto redisDto);

	void deleteValue(RedisDto redisDto);

	void addTokenToBlacklist(String token, long expirationTime);

	boolean isTokenBlacklisted(String token);

	void addRecentSearch(RedisDto redisDto);

	List<Object> getRecentSearches(RedisDto redisDto);
}
