package com.weepl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.WeeNetworkFormDto;
import com.weepl.dto.WeeNetworkSearchDto;
import com.weepl.entity.WeeNetwork;
import com.weepl.repository.WeeNetworkRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WeeNetworkService {

	private final WeeNetworkRepository weeNetworkRepository;

	public List<WeeNetwork> getAllWn() {
		List<WeeNetwork> wnList = weeNetworkRepository.findAll();
		return wnList;
	}

	public void createWeeNetwork(WeeNetwork weeNetwork) {
		weeNetworkRepository.save(weeNetwork);
	}

	public Long saveWeeNetwork(WeeNetworkFormDto weeNetworkFormDto) throws Exception {
		WeeNetwork weeNetwork = weeNetworkFormDto.createWeeNetwork();
		weeNetworkRepository.save(weeNetwork);

		return weeNetwork.getCd();
	}

	public WeeNetwork saveAgency(WeeNetwork weeNetwork) {
		return weeNetworkRepository.save(weeNetwork);
	}
	
	public List<WeeNetwork> searchWeeNetworks(WeeNetworkSearchDto weeNetworkSearchDto) {
	    String agencyName = weeNetworkSearchDto.getAgencyName();
	    String locName = weeNetworkSearchDto.getLocName();
	    List<WeeNetwork> searchResults = weeNetworkRepository.searchByAgencyNameAndLocName(agencyName, locName);

	    return searchResults;
	}
}