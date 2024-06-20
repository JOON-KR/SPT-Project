package com.sptp.dawnary.diary.dto;

import com.sptp.dawnary.diary.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryFormDto {

    private String title;

    private String content;

    private LocalDateTime date;

    private Weather weather;

    private int status;

    private String imagePath;
}
