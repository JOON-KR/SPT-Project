package com.sptp.dawnary.schedule.dto;

import com.sptp.dawnary.location.dto.LocationRequest;

import java.time.LocalDateTime;

public record ScheduleRequest (LocalDateTime date, String title, String content, LocationRequest locationRequest){
}
