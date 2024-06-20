package com.sptp.dawnary.diary.dto;

import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.diary.domain.Weather;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private Long id;
    private String title;

    private String content;

    private LocalDateTime date;

    private Weather weather;

    private int status;

    private String imagePath;

    private Long memberId;

    private String name;

    public static DiaryDto toDto(Diary diary) {
        return DiaryDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .weather(diary.getWeather())
                .status(diary.getStatus())
                .imagePath(diary.getImagePath())
                .memberId(diary.getMember().getId())
                .name(diary.getMember().getName())
                .build();
    }
}
