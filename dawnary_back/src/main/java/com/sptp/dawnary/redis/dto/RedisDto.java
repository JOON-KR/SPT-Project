package com.sptp.dawnary.redis.dto;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RedisDto {
	private String key;
	private String value;
	private Duration duration;
}
