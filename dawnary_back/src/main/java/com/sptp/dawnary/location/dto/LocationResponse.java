package com.sptp.dawnary.location.dto;

import com.sptp.dawnary.location.domain.Location;
import lombok.Builder;

@Builder
public record LocationResponse(Long id, Double latitude, Double longitude, String name){

    public static LocationResponse toResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .name(location.getName())
                .build();
    }

}
