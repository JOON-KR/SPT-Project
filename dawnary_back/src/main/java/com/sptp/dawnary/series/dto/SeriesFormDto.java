package com.sptp.dawnary.series.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesFormDto {

    private String title;

    private String imagePath;

    private int status;

    private List<Long> diaries;
}
