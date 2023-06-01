package com.weepl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.modelmapper.ModelMapper;

import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsNmem;
import com.weepl.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardConsFormDto {
	
	private Long cd;
	
	@NotBlank(message="제목을 입력해 주세요")
	private String title;
	
	
	@NotEmpty(message="비밀번호는 필수 입력 값입니다.")
	private String pwd;
	
	@NotBlank(message="내용을 입력해 주세요.")
	private String content;
	
	@NotBlank(message="이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message="이메일은 필수 입력 값입니다.")
	@Email(message="이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotBlank(message="성별을 선택해주세요.")
	private String gender;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public BoardCons createCons() {
		return modelMapper.map(this, BoardCons.class);
	}
	public BoardConsNmem createConsNmem(BoardConsFormDto boardConsFormDto, BoardCons boardCons) {
		BoardConsNmem boardConsNmem = new BoardConsNmem();
		boardConsNmem.setName(boardConsFormDto.getName());
		boardConsNmem.setEmail(boardConsFormDto.getEmail());
		boardConsNmem.setGender(boardConsFormDto.getGender());
		boardConsNmem.setBoardCons(boardCons);
		
		return boardConsNmem;
	}
	public static BoardConsFormDto of(Member member) {
		BoardConsFormDto boardConsFormDto = new BoardConsFormDto();
		boardConsFormDto.setName(member.getName());
		boardConsFormDto.setEmail(member.getEmail());
		boardConsFormDto.setGender(member.getGender());
		boardConsFormDto.setMemberCd(member.getCd());
		
		return boardConsFormDto;
	}
	
	private Long memberCd;

}
