package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.ServiceProvider;

public interface ProviderRepository extends JpaRepository<ServiceProvider, Long> {

	Optional<ServiceProvider> findByEmail(String email);
	boolean existsByEmail(String email);
}
