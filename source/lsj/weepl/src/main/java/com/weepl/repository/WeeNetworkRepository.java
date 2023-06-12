package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.weepl.entity.WeeNetwork;

public interface WeeNetworkRepository extends JpaRepository<WeeNetwork, Long> {

	
	 @Query("SELECT wn FROM WeeNetwork wn WHERE wn.agencyName LIKE %:agencyName% AND wn.locName LIKE %:locName%")
	 List<WeeNetwork> searchByAgencyNameAndLocName(String agencyName, String locName);
	
}
