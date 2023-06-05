package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로, 부모 클래스를 상속 받는 자식 클래스에 매핑정보만 제공한다.
@Getter
@Setter
public abstract class BaseTimeEntity {
	// 작성 일시
	@CreatedDate // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장한다.
	@Column(updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDt;
	
	// 수정 일시
	@LastModifiedDate // 엔티티 값을 변경할 때 시간을 자동으로 저장한다.
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modDt;
	
}
