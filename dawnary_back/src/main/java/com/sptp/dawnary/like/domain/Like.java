package com.sptp.dawnary.like.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.series.domain.Series;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity(name = "likes")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Like {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    @JsonIgnore
    private Series series;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @PrePersist
    protected void onCreate() {
        regDate = LocalDateTime.now();
    }
}
