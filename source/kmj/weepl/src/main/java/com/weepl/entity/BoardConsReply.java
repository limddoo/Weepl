package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.weepl.dto.BoardConsReplyDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table
public class BoardConsReply extends BaseEntity {
	@Id
	@Column(name="board_cons_reply_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long cd; //고유번호
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="board_cons_cd")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private BoardCons boardCons; //게시판상담글
	
	@Lob
	@Column(nullable=false)
	private String content; //내용
	
	public static BoardConsReply createBoardReply(BoardConsReplyDto boardConsReplyDto) {
		BoardConsReply boardConsReply = new BoardConsReply();
		boardConsReply.setBoardCons(boardConsReplyDto.getBoardCons());
		boardConsReply.setContent(boardConsReplyDto.getContent());
		
		return boardConsReply;
	}
}
