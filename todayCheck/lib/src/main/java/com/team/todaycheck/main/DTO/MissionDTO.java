package com.team.todaycheck.main.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiParam;

@Getter
@Setter
@NoArgsConstructor
public class MissionDTO {
	@ApiParam(value = "식별자")
    private Long id;
	
	@ApiParam(value = "포스트 썸네일")
    private String postPicture;
	
	@ApiParam(value = "진행자 이름")
    private String adminName;
	
	@ApiParam(value = "진행자 아바타")
    private String adminPicture;

	@ApiParam(value = "미션 포스트 타이틀")
    private String postTitle;
	
	@ApiParam(value = "미션 포스트 내용")
    private String postContent;

	@ApiParam(value = "미션 시작 날자")
    private LocalDateTime startDate;
	
	@ApiParam(value = "미션 종료 날자")
    private LocalDateTime endDate;
}
