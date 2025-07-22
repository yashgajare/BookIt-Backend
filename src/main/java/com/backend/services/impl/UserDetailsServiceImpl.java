package com.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.entities.Customer;
import com.backend.entities.ServiceProvider;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.ProviderRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		if(customerRepository.existsByEmail(email)) {
			Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer not found with this email!"));
			return UserDetailsImpl.build(customer);
		}else if(providerRepository.existsByEmail(email)) {
			ServiceProvider provider = providerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Service Provider not found with this email!"));
			return UserDetailsImpl.build(provider);
		}
			
		throw new UsernameNotFoundException("User not found with email: " + email);
	}

}
