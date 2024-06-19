package com.sptp.dawnary.series.domain;


import java.util.Date;

import com.sptp.dawnary.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Series {
	   private long id;
	    private Member member;
	    private String title;
	    private int status;
	    private String imagePath;
	    private int viewCnt;
	    private Date regDate;
}
