package com.sptp.dawnary.series.dto;

import lombok.*;

import java.util.List;

@Builder
public record SeriesRequest(String title, String imagePath, int status, List<Long> diaries) {
}
