package com.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.AvailabilityScheduleDto;
import com.backend.dtos.BusinessAddressDto;
import com.backend.dtos.BusinessCategoryDto;
import com.backend.dtos.BusinessInfoDto;
import com.backend.dtos.PortfolioImageDto;
import com.backend.services.ProviderRegistrationService;

@RestController
@RequestMapping("/api/business")
public class ProviderRegistrationController  {

	@Autowired
	private ProviderRegistrationService registrationService;
	
//	@PostMapping("/step1")
//	public ResponseEntity<?> saveBusinessInfo(@RequestBody BusinessInfoDto businessInfoDto){
//		
//	}
//	
//	@PostMapping("/step2/{draftId}")
//	public ResponseEntity<?> saveBusinessCategory(@RequestBody BusinessCategoryDto categoryDto){
//		
//	}
//	
//	@PostMapping("/step3/{draftId}")
//	public ResponseEntity<?> saveBusinessAddress(@RequestBody BusinessAddressDto addressDto){
//		
//	}
//	
//	@PostMapping("/step4/{draftId}")
//	public ResponseEntity<?> saveBusinessAvailabiltySchedule(@RequestBody AvailabilityScheduleDto scheduleDto){
//		
//	}
//	
//	@PostMapping("/step5/{draftId}")
//	public ResponseEntity<?> saveBusinessPortfolioImage(@RequestBody PortfolioImageDto portfolioImageDto){
//		
//	}
	
//	@PostMapping("/saveBusinessDetails/{draftId}")
//	public ResponseEntity<?> saveBusinessDetails(@PathVariable Long draftId){
//		
//	}
	
}
