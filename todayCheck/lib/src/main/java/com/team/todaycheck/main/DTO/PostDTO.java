package com.team.todaycheck.main.DTO;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.todaycheck.main.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
	private String title;
	private String writer;
	private String description;
	private String thumbnail;
	@JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Seoul")
	private Date date;
	private int postKey;
	private int views;
	private int recommendation;
	private List<Comment> comment;
	private ResponseEntity<byte[]> image;
	@Override
	public String toString() {
		return "PostDTO [title=" + title + ", writer=" + writer + ", description=" + description + ", thumbnail="
				+ thumbnail + ", date=" + date + ", postKey=" + postKey + "]";
	}
	
}
