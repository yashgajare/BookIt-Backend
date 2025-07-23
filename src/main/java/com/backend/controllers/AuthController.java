package com.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.MessageResponse;
import com.backend.security.request.OtpVerificationRequest;
import com.backend.security.request.SignupRequest;
import com.backend.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
//	@PostMapping("/public/login")
//	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//		
//	}
	
	@PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            if (signupRequest.getEmail() == null && signupRequest.getMobileNumber() == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Please provide Email or Mobile Number"));
            }
            System.out.println("SignupRequest: "+ signupRequest.getEmail());
            MessageResponse response = authService.registerUser(signupRequest);
            
            if (response.getMessage().startsWith("Error")) {
                return ResponseEntity.badRequest().body(response);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse("Internal Server Error"));
        }
    }
	
	@PostMapping("/public/validateOtp")
	public ResponseEntity<?> validateOtp(@RequestBody OtpVerificationRequest request) {
	    try {
	        MessageResponse response = authService.validateOtp(request.getEmail(), request.getOtp());

	        if (response.getMessage().startsWith("Error")) {
	            return ResponseEntity.badRequest().body(response);
	        }

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body(new MessageResponse("Internal Server Error"));
	    }
	}


}
