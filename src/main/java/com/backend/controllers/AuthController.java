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
	
//	@PostMapping("/public/login")
//	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//		
//	}
	
//	@PostMapping("/public/register")
//	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
//		
//		
//	}
	
//	@PostMapping("/public/validateOtp")
//	public ResponseEntity<?> validateOtp(@RequestParam Integer otp){
//		
//	}

}
