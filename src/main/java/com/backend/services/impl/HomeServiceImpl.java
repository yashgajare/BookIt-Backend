package com.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.dtos.PopularCategoryDto;
import com.backend.dtos.SearchResultDto;
import com.backend.entities.ServiceOffered;
import com.backend.entities.ServiceProvider;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.ServiceOfferedRepository;

@Service
public class HomeServiceImpl {

	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private ServiceOfferedRepository serviceRepository;
	
	public Page<PopularCategoryDto> getPopularCategories(Pageable pageable) {
        return providerRepository.findPopularCategories(pageable);
    }
	
	public SearchResultDto search(String query, Pageable pageable) {
		try {
			Page<ServiceProvider> providers = providerRepository
					.findByBusinessNameContainingIgnoreCaseOrOwnerNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, query, pageable);
			
			Page<ServiceOffered> services = serviceRepository
					.findByServiceNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);
			
			return new SearchResultDto(providers.getContent(), services.getContent());
		} catch (Exception e) {
			throw new RuntimeException("Search operation failed: " + e.getMessage(), e);
		}
	}
	
}
