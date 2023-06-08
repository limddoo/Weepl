package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@Table(name="board_cons_Nmem")
@ToString
public class BoardConsNmem {
	
	@Id
	@Column(name="nmem_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private String name;
	
	private String gender;
	
	private String email;
	
	private String region;

}
