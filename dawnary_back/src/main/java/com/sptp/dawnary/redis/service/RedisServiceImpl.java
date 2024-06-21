package com.sptp.dawnary.redis.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.sptp.dawnary.redis.dto.RedisDto;

@Service
public class RedisServiceImpl implements RedisService {
	
	private static final String RECENT_SEARCH_KEY_PREFIX = "recent_search:";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void setValues(RedisDto redisDto) {
		String key = redisDto.getKey();
		Object value = redisDto.getValue();
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void setValuesAddTime(RedisDto redisDto) {
		String key = redisDto.getKey();
		Object value = redisDto.getValue();
		Duration timeout = redisDto.getDuration();
		redisTemplate.opsForValue().set(key, value, timeout);
	}

	@Override
	public String getValue(RedisDto redisDto) {
		String key = redisDto.getKey();
		Object value = redisTemplate.opsForValue().get(key);
		return value != null ? value.toString() : null;
	}

	@Override
	public void deleteValue(RedisDto redisDto) {
		String key = redisDto.getKey();
		redisTemplate.delete(key);
	}

	public void addRecentSearch(Long Id, String keyword) {
		String key = RECENT_SEARCH_KEY_PREFIX + Id;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		listOperations.leftPush(key, keyword);
	}

	public List<Object> getRecentSearches(Long Id) {
		String key = RECENT_SEARCH_KEY_PREFIX + Id;
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		return listOperations.range(key, 0, -1); // -1은 리스트의 끝까지 모두 가져오라는 의미
	}

}
