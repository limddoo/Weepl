package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.modelmapper.ModelMapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "SWEETCOMM")
public class SweetComment extends BaseEntity {
	
	@Id
	@Column(name="comm_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;	// 댓글 고유번호
	
	@Lob
	@Column(name="comm_content", nullable = false)
	private String comment;
	
	// 댓글 삭제 여부
	@Column(nullable = false)
	private String del_yn;
	
	// 댓글 작성자
	private String createdBy;
	
	@ManyToOne
	@JoinColumn(name="mem_cd")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="sweet_board_cd")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SweetBoard sweetBoard;
	
	public void updateComment(String comment) {
		this.comment = comment;
	}

	private static ModelMapper modelMapper = new ModelMapper();
	
	public SweetComment createSweetComment() {
		return modelMapper.map(this, SweetComment.class);		// dto를 entity로
	}
}
