package com.sptp.dawnary.series.domain;

import com.sptp.dawnary.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Series {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
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

	@PrePersist
	protected void onCreate() {
		regDate = LocalDateTime.now();
	}
}