package com.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.security.request.LoginRequest;
import com.backend.security.request.SignupRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@PostMapping("/public/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
		
	}
	
	@PostMapping("/public/register")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
		
	}
	
	@PostMapping("/public/validateOtp")
	public ResponseEntity<?> validateOtp(@RequestParam Integer otp){
		
	}

}
