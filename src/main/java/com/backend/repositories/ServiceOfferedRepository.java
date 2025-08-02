package com.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.ServiceOffered;

public interface ServiceOfferedRepository extends JpaRepository<ServiceOffered, Long>{

	Page<ServiceOffered> findByServiceNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String serviceName, String description, Pageable pageable);
	
}
