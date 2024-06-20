package com.sptp.dawnary.location.dto;


import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private String name;
}
