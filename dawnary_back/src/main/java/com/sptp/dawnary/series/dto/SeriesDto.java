package com.sptp.dawnary.series.dto;

import com.sptp.dawnary.diary.dto.DiaryDto;
import com.sptp.dawnary.like.domain.Like;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesDto {

    private Long id;

    private Long memberId;

    private String name;

    private String title;

    private String imagePath;

    private int status;

    private long viewCnt;

    private LocalDateTime regDate;

    private List<DiaryDto> diaries;
}
