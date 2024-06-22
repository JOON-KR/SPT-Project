package com.sptp.dawnary.diary.dto;

import com.sptp.dawnary.diary.domain.Weather;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DiaryRequest(String title, String content, LocalDateTime date, Weather weather, int status, String imagePath) {

}
