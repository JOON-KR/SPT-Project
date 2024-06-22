package com.sptp.dawnary.schedule.dto;

import com.sptp.dawnary.location.dto.LocationResponse;
import com.sptp.dawnary.schedule.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleResponse(Long id, LocalDateTime date, String title, String content, LocationResponse locationResponse){

    public static ScheduleResponse toResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .locationResponse(schedule.getLocation() != null ? LocationResponse.toResponse(schedule.getLocation()) : null)
                .build();
    }
}
