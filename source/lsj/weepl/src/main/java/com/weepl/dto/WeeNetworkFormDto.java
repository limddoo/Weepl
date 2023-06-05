package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.WeeNetwork;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class WeeNetworkFormDto {

	private Long cd;
	private String locName;
	private String agencyName;
	private String agencyTel;
	private String addrDtl;
	private double lati;
	private double longi;
	private String url;
	
	public WeeNetworkFormDto() {
	}
	
	private static ModelMapper modelMapper = new ModelMapper();

	public WeeNetwork createWeeNetwork() {
		return modelMapper.map(this, WeeNetwork.class);
	}
	
	public static WeeNetworkFormDto of(WeeNetwork weeNetwork) {
		return modelMapper.map(weeNetwork, WeeNetworkFormDto.class);
	} // 
	
	static public WeeNetworkFormDto createAgencySeoul1() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("SEOUL");
		weeNetworkFormDto.setAgencyName("도봉Wee센터");
		weeNetworkFormDto.setAddrDtl("서울특별시 도봉구 도봉로115길 16");
		weeNetworkFormDto.setAgencyTel("02-949-7887");
		weeNetworkFormDto.setUrl("http://bbedu.sen.go.kr/");
		weeNetworkFormDto.setLongi(127.033201);
		weeNetworkFormDto.setLati(37.6493268);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencySeoul2() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("SEOUL");
		weeNetworkFormDto.setAgencyName("양천Wee센터");
		weeNetworkFormDto.setAddrDtl("서울특별시 양천구 월정로 269");
		weeNetworkFormDto.setAgencyTel("02-3661-5955");
		weeNetworkFormDto.setUrl("http://gsycedu.sen.go.kr/");
		weeNetworkFormDto.setLongi(126.829438);
		weeNetworkFormDto.setLati(37.5413939);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencySeoul3() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("SEOUL");
		weeNetworkFormDto.setAgencyName("위드Wee센터");
		weeNetworkFormDto.setAddrDtl("서울특별시 서초구 서초대로46길 88 푸른나무재단 3층");
		weeNetworkFormDto.setAgencyTel("02-598-1610");
		weeNetworkFormDto.setUrl("http://www.btf.or.kr/");
		weeNetworkFormDto.setLongi(127.012848);
		weeNetworkFormDto.setLati(37.4898643);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencySeoul4() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("SEOUL");
		weeNetworkFormDto.setAgencyName("중부Wee센터");
		weeNetworkFormDto.setAddrDtl("서울특별시 용산구 두텁바위로74");
		weeNetworkFormDto.setAgencyTel("070-4366-3668");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(126.981048);
		weeNetworkFormDto.setLati(37.5457700);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencySeoul5() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("SEOUL");
		weeNetworkFormDto.setAgencyName("강동Wee센터");
		weeNetworkFormDto.setAddrDtl("서울특별시 강동구 천중로57");
		weeNetworkFormDto.setAgencyTel("070-4366-3668");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(127.127814);
		weeNetworkFormDto.setLati(37.5456680);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaejeon() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEJEON");
		weeNetworkFormDto.setAgencyName("대전광역시교육청Wee센터");
		weeNetworkFormDto.setAddrDtl("대전광역시 서구 둔산로 89 (둔산동)");
		weeNetworkFormDto.setAgencyTel("042-616-8180");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(127.383794);
		weeNetworkFormDto.setLati(36.3520788);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaejeon2() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEJEON");
		weeNetworkFormDto.setAgencyName("대전 동부교육지원청Wee센터");
		weeNetworkFormDto.setAddrDtl("대전광역시 중구 문화로234번길 34");
		weeNetworkFormDto.setAgencyTel("042-229-1250");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(127.409927);
		weeNetworkFormDto.setLati(36.3153508);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaejeon3() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEJEON");
		weeNetworkFormDto.setAgencyName("대전 서부교육지원청Wee센터");
		weeNetworkFormDto.setAddrDtl("대전광역시 서구 계백로 1419 (도마동)");
		weeNetworkFormDto.setAgencyTel("042-530-1004");
		weeNetworkFormDto.setUrl("https://www.djsbe.go.kr/home/main.do?screenTp=USER");
		weeNetworkFormDto.setLongi(127.378883);
		weeNetworkFormDto.setLati(36.3132269);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaegu() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEGU");
		weeNetworkFormDto.setAgencyName("경북Wee센터");
		weeNetworkFormDto.setAddrDtl("대구광역시 북구 호국로 807 (학정동)");
		weeNetworkFormDto.setAgencyTel("053-326-9279");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(128.564853);
		weeNetworkFormDto.setLati(35.9564659);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaegu2() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEGU");
		weeNetworkFormDto.setAgencyName("대동Wee센터");
		weeNetworkFormDto.setAddrDtl("대구광역시 동구 화랑로 169 (효목동)");
		weeNetworkFormDto.setAgencyTel("053-746-7379");
		weeNetworkFormDto.setUrl("https://cafe.daum.net/ddhospwee");
		weeNetworkFormDto.setLongi(128.643939);
		weeNetworkFormDto.setLati(35.8764168);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyDaegu3() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("DAEGU");
		weeNetworkFormDto.setAgencyName("영남Wee센터");
		weeNetworkFormDto.setAddrDtl("대구광역시 남구 현충로 170 (대명동)");
		weeNetworkFormDto.setAgencyTel("053-217-2323");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(128.583532);
		weeNetworkFormDto.setLati(35.8470666);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyBusan() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("BUSAN");
		weeNetworkFormDto.setAgencyName("부산 북부교육지원청Wee센터");
		weeNetworkFormDto.setAddrDtl("부산광역시 북구 백양대로1016번다길 44 (구포동)");
		weeNetworkFormDto.setAgencyTel("051-330-1361");
		weeNetworkFormDto.setUrl("https://home.pen.go.kr/bukbu/main.do");
		weeNetworkFormDto.setLongi(128.995130);
		weeNetworkFormDto.setLati(35.1941700);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyBusan2() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("BUSAN");
		weeNetworkFormDto.setAgencyName("부산 해운대교육지원청Wee센터");
		weeNetworkFormDto.setAddrDtl("부산광역시 북구 백양대로1016번다길 44 (구포동)");
		weeNetworkFormDto.setAgencyTel("051-709-0481");
		weeNetworkFormDto.setUrl("https://home.pen.go.kr/haeundae/main.do");
		weeNetworkFormDto.setLongi(129.175374);
		weeNetworkFormDto.setLati(35.1768564);
		
		return weeNetworkFormDto;
	}
	
	static public WeeNetworkFormDto createAgencyBusan3() {
		WeeNetworkFormDto weeNetworkFormDto = new WeeNetworkFormDto();
		weeNetworkFormDto.setLocName("BUSAN");
		weeNetworkFormDto.setAgencyName("부산 남부교육지원청Wee센터");
		weeNetworkFormDto.setAddrDtl("부산광역시 남구 못골로 29 (대연동)");
		weeNetworkFormDto.setAgencyTel("051-640-0205");
		weeNetworkFormDto.setUrl("");
		weeNetworkFormDto.setLongi(129.085523);
		weeNetworkFormDto.setLati(35.1361814);
		
		return weeNetworkFormDto;
	}
}
