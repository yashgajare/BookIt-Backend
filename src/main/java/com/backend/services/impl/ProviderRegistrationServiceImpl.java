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
import com.backend.entities.Address;
import com.backend.entities.Category;
import com.backend.entities.Customer;
import com.backend.entities.ServiceProvider;
import com.backend.entities.ServiceProviderDraft;
import com.backend.entities.Subcategory;
import com.backend.exceptions.ResourceNotFoundException;
import com.backend.repositories.CategoryRepository;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.ServiceProviderDraftRepository;
import com.backend.repositories.SubcategoryRepository;
import com.backend.services.ProviderRegistrationService;


@Service
public class ProviderRegistrationServiceImpl  implements ProviderRegistrationService {

	@Autowired
	private ServiceProviderDraftRepository draftRepository;
    
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SubcategoryRepository subcategoryRepository;
	
	@Override
	public Long saveBusinessInfo(BusinessInfoDto infoDto) {
		Customer customer= customerRepository.findById(infoDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
		ServiceProviderDraft draft = new ServiceProviderDraft();
		draft.setProvider_id(customer.getCustomerId());
		draft.setBusinessName(infoDto.getBusinessName());
		draft.setEmail(infoDto.getEmail());
		draft.setMobileNumber(infoDto.getMobile_number());
		draft.setOwnerName(infoDto.getOwnerName());
		draftRepository.save(draft);
		return draft.getId();
	}

	@Override
	public String saveBusinessCategory(BusinessCategoryDto categoryDto) {
		ServiceProviderDraft draft = draftRepository.findById(categoryDto.getProvider_id()).orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
		Category category = categoryRepository.findById(categoryDto.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		
		List<Subcategory> subcategories = new ArrayList<>();
		if(categoryDto.getSubcategory()!=null && !categoryDto.getSubcategory().isEmpty()) {
			for(String sub: categoryDto.getSubcategory()) {
				Subcategory subCategory = subcategoryRepository.findByNameAndCategory(sub, category)
						.orElseThrow(() -> new ResourceNotFoundException("Subcategory '" + sub + "' not found for category '" + category.getName() + "'"));
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
		ServiceProviderDraft draft = draftRepository.findById(addressDto.getProviderId()).orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
		String addressLine = addressDto.getAddressLine1() != null ? 
				addressDto.getAddressLine1() : "" + addressDto.getAddressLine2() != null ?
						addressDto.getAddressLine2() : "";
		Address address = new Address();
		address.setAddressLine(addressLine);
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setPincode(addressDto.getPincode());
		draft.setBusinessAddress(address);
		draftRepository.save(draft);
		return "Address saved successfully";
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
