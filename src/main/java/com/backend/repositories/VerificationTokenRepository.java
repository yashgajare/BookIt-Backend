package com.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.dtos.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByEmail(String email);

	Optional<VerificationToken> findByToken(String token);

	void deleteByEmail(String email);
}
