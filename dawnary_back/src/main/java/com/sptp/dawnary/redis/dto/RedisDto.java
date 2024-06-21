package com.sptp.dawnary.redis.dto;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RedisDto {
	private String key;
	private String value;
	private Duration duration;
}
