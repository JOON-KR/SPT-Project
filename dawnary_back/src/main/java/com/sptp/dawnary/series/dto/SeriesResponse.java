package com.sptp.dawnary.series.dto;

import com.sptp.dawnary.diary.dto.DiaryResponse;
import com.sptp.dawnary.series.domain.Series;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SeriesResponse (Long id, Long memberId, String name,
                             String title, String imagePath, int status,
                             long viewCnt, LocalDateTime regDate, List<DiaryResponse> diaries){

    public static SeriesResponse toResponse(Series series, List<DiaryResponse> diaryResponses) {
        return SeriesResponse.builder()
                .id(series.getId())
                .memberId(series.getMember().getId())
                .name(series.getMember().getName())
                .title(series.getTitle())
                .imagePath(series.getImagePath())
                .status(series.getStatus())
                .viewCnt(series.getViewCnt())
                .regDate(series.getRegDate())
                .diaries(diaryResponses)
                .build();
    }
}
