package com.sptp.dawnary.redis.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.sptp.dawnary.redis.dto.RedisDto;

@Service
public interface RedisService {

    void setValues(RedisDto redisDto);                       // 값 등록 / 수정

    void setValuesAddTime(RedisDto redisDto);    // 값 등록 / 수정

    String getValue(RedisDto redisDto);                                    // 값 조회

    void deleteValue(RedisDto redisDto);                                   // 값 삭제
}