package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chatbot")
@Getter
@Setter
@ToString
public class Chatbot {
	@Id
	@Column(name = "cb_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private Long relatedCd;
	
	private String content;
}
