package com.sptp.dawnary.series.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sptp.dawnary.like.domain.Like;
import com.sptp.dawnary.member.domain.Member;
import com.sptp.dawnary.series.dto.SeriesRequest;
import com.sptp.dawnary.seriesDiary.domain.SeriesDiary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Series {

	private final static String DEFAULT_IMAGE_PATH = "default.png";

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

	@Column(name = "image_path", nullable = false)
	private String imagePath;

	@Column(name = "status", nullable = false)
	@ColumnDefault("1")
	private int status;

	@Column(name = "view_cnt", nullable = false)
	@ColumnDefault("0")
	private long viewCnt;

	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;

	@OneToMany(mappedBy = "series", fetch = LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Like> likes;

	@OneToMany(mappedBy = "series", fetch = LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<SeriesDiary> seriesDiaries;

	@PrePersist
	protected void onCreate() {
		regDate = LocalDateTime.now();
	}

	public static Series toEntity(SeriesRequest seriesRequest) {
		return Series.builder()
				.title(seriesRequest.title())
				.imagePath(seriesRequest.imagePath() != null ? seriesRequest.imagePath() : DEFAULT_IMAGE_PATH)
				.status(seriesRequest.status())
//				.seriesDiaries()
				.build();
	}
}