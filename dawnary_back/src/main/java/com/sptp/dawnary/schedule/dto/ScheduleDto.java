package com.sptp.dawnary.schedule.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDto {

    private Long id;

    private LocalDateTime date;

    private String title;

    private String content;
}
