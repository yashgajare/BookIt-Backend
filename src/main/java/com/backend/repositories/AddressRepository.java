package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
