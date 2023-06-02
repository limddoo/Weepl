package com.weepl.dto;

import javax.validation.constraints.NotEmpty;

import com.weepl.entity.BoardCons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardConsReplyDto {
	private BoardCons boardCons;
	
	@NotEmpty(message="내용을 입력해주세요")
	private String content;
}
