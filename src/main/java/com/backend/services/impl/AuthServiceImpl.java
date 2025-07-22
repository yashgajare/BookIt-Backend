package com.backend.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.dtos.MessageResponse;
import com.backend.entities.Customer;
import com.backend.entities.Role;
import com.backend.enums.RoleType;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.RoleRepository;
import com.backend.security.request.SignupRequest;
import com.backend.services.AuthService;

public class AuthServiceImpl implements AuthService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private RoleRepository roleRepository;

	@Override
	public MessageResponse registerUser(SignupRequest signupRequest) {
		
		if(customerRepository.existsByEmail(signupRequest.getEmail())) {
			return new MessageResponse("Error: Email is already in use!");
		}
		
		if(providerRepository.existsByEmail(signupRequest.getEmail())) {
			return new MessageResponse("Error: Email is already in use!");
		}
		
		Customer customer = new Customer(signupRequest.getFullName(),
				signupRequest.getEmail(), signupRequest.getMobileNumber(), passwordEncoder.encode(signupRequest.getPassword()));
		
		 Set<String> strRoles = signupRequest.getRole();
		    Set<Role> roles = new HashSet<>();

		    if (strRoles == null || strRoles.isEmpty()) {
		        // Set default role to CUSTOMER
		        Role customerRole = roleRepository.findByRoleName(RoleType.ROLE_CUSTOMER)
		            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		        roles.add(customerRole);
		    } 
		    
		 customer.setRoles(roles);
		 customerRepository.save(customer);
		 
		 // create otp generate method
		 
		 
		 return new MessageResponse("Proceed to Email Verification");
	}
	
}
