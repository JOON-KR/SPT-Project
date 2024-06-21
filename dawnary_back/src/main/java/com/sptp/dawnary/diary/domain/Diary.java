package com.sptp.dawnary.diary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "weather", nullable = false)
    @Enumerated(EnumType.STRING)
    private Weather weather;

    @Column(name = "sentiment", nullable = false)
    private double sentiment;

    @Column(name = "status", nullable = false)
    @ColumnDefault("1")
    private int status;

    @Column(name = "image_path", nullable = true)
    private String imagePath;

    @OneToMany(mappedBy = "diary", fetch = LAZY, cascade = CascadeType.ALL)
    private List<SeriesDiary> seriesDiaries;

}
