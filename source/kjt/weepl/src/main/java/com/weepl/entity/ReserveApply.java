package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ReserveApply {
    @Id
    @Column(name="reserve_apply_cd")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reserveApplyCd;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mem_cd")
    private Member memberCd;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reserve_schedule_cd")
    private ReserveSchedule reserveSchedule;
    
    private String reserveId;
    
    private String reserveTitle;
    
    @Lob
    @Column(nullable=false)
    private String consReqContent;
    
    @CreatedDate
    //@Column(nullable=false)
    private LocalDateTime reserveDt;
    
    @Column(nullable=false)
    private String reserveStatus;    
    
    private LocalDateTime cancDt;
}