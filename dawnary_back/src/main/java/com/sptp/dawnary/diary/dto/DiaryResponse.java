package com.sptp.dawnary.diary.dto;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.domain.Weather;
import lombok.*;

import java.time.LocalDateTime;

@Builder
public record DiaryResponse(Long id, String title, String content, LocalDateTime date,
                            Weather weather, int status, Double sentiment, String imagePath, Long memberId, String name) {

    public static DiaryResponse toResponse(Diary diary) {
        return DiaryResponse.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .sentiment(diary.getSentiment())
                .date(diary.getDate())
                .weather(diary.getWeather())
                .status(diary.getStatus())
                .imagePath(diary.getImagePath())
                .memberId(diary.getMember().getId())
                .name(diary.getMember().getName())
                .build();
    }
}