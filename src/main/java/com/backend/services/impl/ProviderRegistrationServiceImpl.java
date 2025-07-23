package com.backend.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.AvailabilityScheduleDto;
import com.backend.dtos.BusinessAddressDto;
import com.backend.dtos.BusinessCategoryDto;
import com.backend.dtos.BusinessInfoDto;
import com.backend.dtos.PortfolioImageDto;
import com.backend.entities.Category;
import com.backend.entities.ServiceProvider;
import com.backend.entities.ServiceProviderDraft;
import com.backend.entities.Subcategory;
import com.backend.repositories.CategoryRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.ServiceProviderDraftRepository;
import com.backend.services.ProviderRegistrationService;


@Service
public class ProviderRegistrationServiceImpl  implements ProviderRegistrationService {

	@Autowired
	private ServiceProviderDraftRepository draftRepository;
    
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Long saveBusinessInfo(BusinessInfoDto infoDto) {
		ServiceProvider provider = providerRepository.findById(infoDto.getProviderId()).orElseThrow(() -> new RuntimeException("Provider not found"));
		ServiceProviderDraft draft = new ServiceProviderDraft();
		draft.setProvider_id(provider.getProviderId());
		draft.setBusinessName(infoDto.getBusinessName());
		draft.setEmail(infoDto.getEmail());
		draft.setMobileNumber(infoDto.getMobile_number());
		draft.setOwnerName(infoDto.getOwnerName());
		draftRepository.save(draft);
		return draft.getId();
	}

	@Override
	public String saveBusinessCategory(BusinessCategoryDto categoryDto) {
		ServiceProviderDraft draft = draftRepository.findById(categoryDto.getProvider_id()).orElseThrow(() -> new RuntimeException("Provider not found"));
		Category category = categoryRepository.findById(categoryDto.getCategory_id()).orElseThrow(() -> new RuntimeException("Category not found"));
		
		List<Subcategory> subcategories = new ArrayList<>();
		if(categoryDto.getSubcategory()!=null && !categoryDto.getSubcategory().isEmpty()) {
			for(String sub: categoryDto.getSubcategory()) {
				Subcategory subCategory = categoryRepository.findByNameAndCategory(sub, category)
						.orElseThrow(() -> new RuntimeException("Subcategory '" + sub + "' not found for category '" + category.getName() + "'"));
				subcategories.add(subCategory);
			}
			draft.setSubcategories(subcategories);
		}
		draft.setCategory(category);
		draftRepository.save(draft);
		
		return "Category saved successfully";
	}

	@Override
	public String saveBusinessAddress(BusinessAddressDto addressDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveBusinessAvailabiltySchedule(AvailabilityScheduleDto scheduleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveBusinessPortfolioImage(PortfolioImageDto imageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveBusinessDetails(Long draftId) {
		// TODO Auto-generated method stub
		return null;
	}

}
