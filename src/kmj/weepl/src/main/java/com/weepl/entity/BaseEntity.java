package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity {
	// 작성자
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	// 수정자
	@LastModifiedBy
	private String modifiedBy;

}