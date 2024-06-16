package com.sptp.dawnary.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity(name = "series_diary")
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;
}
