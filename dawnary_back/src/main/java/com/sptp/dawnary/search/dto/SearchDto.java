package com.sptp.dawnary.search.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDto {
	private String type; //member / series
	private Long id;
	private String name;
	private String email;
	private String imagePath;
	private String title;
	private LocalDateTime regDate;
}
