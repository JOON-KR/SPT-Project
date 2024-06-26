package com.sptp.dawnary.schedule.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sptp.dawnary.location.domain.Location;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.schedule.dto.ScheduleRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = LAZY)
    @JsonIgnore
    private Location location;

    public static Schedule toEntity(ScheduleRequest scheduleRequest) {
        return Schedule.builder()
                .date(scheduleRequest.date())
                .title(scheduleRequest.title())
                .content(scheduleRequest.content())
                .build();
    }

}
