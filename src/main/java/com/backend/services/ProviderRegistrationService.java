package com.backend.services;

import com.backend.dtos.AvailabilityScheduleDto;
import com.backend.dtos.BusinessAddressDto;
import com.backend.dtos.BusinessCategoryDto;
import com.backend.dtos.BusinessInfoDto;
import com.backend.dtos.PortfolioImageDto;

public interface ProviderRegistrationService  {

	public Long saveBusinessInfo(BusinessInfoDto infoDto);
	
	public String saveBusinessCategory(BusinessCategoryDto categoryDto);
	
	public String saveBusinessAddress(BusinessAddressDto addressDto);
	
	public String saveBusinessAvailabiltySchedule(AvailabilityScheduleDto scheduleDto);
	
	public String saveBusinessPortfolioImage(PortfolioImageDto imageDto);
	
	public String saveBusinessDetails(Long draftId);
	
}
