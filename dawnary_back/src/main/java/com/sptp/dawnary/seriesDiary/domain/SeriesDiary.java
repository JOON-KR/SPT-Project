package com.sptp.dawnary.seriesDiary.domain;


import com.sptp.dawnary.diary.domain.Diary;
import com.sptp.dawnary.series.domain.Series;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity(name = "series_diary")
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDiary {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;
}
