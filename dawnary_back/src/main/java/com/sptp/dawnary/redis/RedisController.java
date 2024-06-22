package com.sptp.dawnary.redis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sptp.dawnary.redis.dto.RedisDto;
import com.sptp.dawnary.redis.service.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/redis")
@Slf4j
public class RedisController {
	private final RedisService redisService;
	
	@PostMapping
	
	public ResponseEntity<?> test (@RequestBody RedisDto redisDto){
		log.info("redis : {}",redisDto.toString());
		redisService.setValues(redisDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping()
	public ResponseEntity<?> get( RedisDto redisDto){
		String res = redisService.getValue(redisDto);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
}
