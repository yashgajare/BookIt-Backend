package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.dtos.PopularCategoryDto;
import com.backend.entities.ServiceProvider;

public interface ProviderRepository extends JpaRepository<ServiceProvider, Long> {

	Optional<ServiceProvider> findByEmail(String email);
	boolean existsByEmail(String email);
	
	@Query("SELECT new com.backend.dtos.PopularCategoryDto(c.categoryId, c.name, c.iconUrl, COUNT(sp)) " +
		       "FROM ServiceProvider sp JOIN sp.category c " +
		       "GROUP BY c.id, c.name, c.iconUrl " +
		       "ORDER BY COUNT(sp) DESC")
	Page<PopularCategoryDto> findPopularCategories(Pageable pageable);
	
	Page<ServiceProvider> findByBusinessNameContainingIgnoreCaseOrOwnerNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String businessName, String ownerName, String description, Pageable pageable);
	
}
