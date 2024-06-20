package com.sptp.dawnary.location.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sptp.dawnary.schedule.domain.Schedule;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    @JsonIgnore
    private Schedule schedule;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "name", nullable = true)
    private String name;

}
