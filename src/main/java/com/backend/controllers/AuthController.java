package com.backend.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.MessageResponse;
import com.backend.entities.Customer;
import com.backend.entities.Role;
import com.backend.enums.RoleType;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.RoleRepository;
import com.backend.security.request.LoginRequest;
import com.backend.security.request.SignupRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired RoleRepository roleRepository;
	
//	@PostMapping("/public/login")
//	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//		
//	}
	
	@PostMapping("/public/register")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
		
		if(customerRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		
		if(providerRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
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
		 return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
	}
	
//	@PostMapping("/public/validateOtp")
//	public ResponseEntity<?> validateOtp(@RequestParam Integer otp){
//		
//	}

}
