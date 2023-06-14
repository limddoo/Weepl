package com.weepl.dto;

import com.weepl.entity.Member;
import com.weepl.entity.MhTestResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MhTestResultDto {
	
	private Long cd;
	private String major_div;
	private String mid_div;
	private String minor_div;
	private String result_content;
	private Long memberCd;
	
	public MhTestResult createResult(MhTestResultDto mhTestResultDto, Member member) {
		MhTestResult mhTestResult = new MhTestResult();
		mhTestResult.setCd(cd);
		mhTestResult.setMajor_div(major_div);
		mhTestResult.setMid_div(mid_div);
		mhTestResult.setMinor_div(minor_div);
		mhTestResult.setResult_content(result_content);
		mhTestResult.setMember(member);
		
		return mhTestResult;
	}
	
	
}
