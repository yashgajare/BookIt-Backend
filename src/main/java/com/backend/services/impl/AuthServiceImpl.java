package com.backend.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.dtos.MessageResponse;
import com.backend.entities.Customer;
import com.backend.entities.Role;
import com.backend.entities.ServiceProvider;
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

        // Check if email already exists in customer or provider tables
        if (customerRepository.existsByEmail(signupRequest.getEmail()) || 
            providerRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Create Customer entity
        Customer customer = new Customer(
                signupRequest.getFullName(),
                signupRequest.getEmail(),
                signupRequest.getMobileNumber(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        // Set default role(s)
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role customerRole = roleRepository.findByRoleName(RoleType.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Default role not found."));
            roles.add(customerRole);
        } else {
            for (String roleName : strRoles) {
                RoleType roleType = RoleType.valueOf(roleName.toUpperCase());
                Role role = roleRepository.findByRoleName(roleType)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " not found."));
                roles.add(role);
            }
        }

        customer.setRoles(roles);
        customerRepository.save(customer);

        // Generate OTP and send for email/mobile verification

        return new MessageResponse("Proceed to Email Verification");
    }

	@Override
	public void generateOtp(String email) {
		
		Customer customer = null;
		if(customerRepository.existsByEmail(email)) {
			customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: Email not found"));
		}
		
		ServiceProvider provider = null;
		if(providerRepository.existsByEmail(email)) {
			provider = providerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Error: Email not found"));
		}
	}
	
}
