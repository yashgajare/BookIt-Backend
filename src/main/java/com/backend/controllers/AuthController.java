package com.backend.controllers;

import com.backend.security.request.LoginRequest;
import com.backend.security.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.ApiResponse;
import com.backend.dtos.MessageResponse;
import com.backend.dtos.StandardResponse;
import com.backend.security.request.OtpVerificationRequest;
import com.backend.security.request.SignupRequest;
import com.backend.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/public/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
	    try {
	        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
	            ApiResponse<Object> apiResponse = ApiResponse.builder()
	                    .message("Please provide both Email and Password")
	                    .status(HttpStatus.BAD_REQUEST)
	                    .error("Missing credentials")
	                    .build();
	            return ResponseEntity.badRequest().body(new StandardResponse<>(apiResponse));
	        }

	        LoginResponse response = authService.loginUser(loginRequest);

	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Login successful")
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(response)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(apiResponse));

	    } catch (Exception e) {
	        e.printStackTrace();
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Internal Server Error")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(apiResponse));
	    }
	}

	
	@PostMapping("/public/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
	    try {
	        MessageResponse messageResponse = authService.registerUser(signupRequest);

	        if (messageResponse.getMessage().startsWith("Error")) {
	            ApiResponse<Object> apiResponse = ApiResponse.builder()
	                    .message(messageResponse.getMessage())
	                    .status(HttpStatus.BAD_REQUEST)
	                    .error("Validation failed")
	                    .build();
	            return ResponseEntity.badRequest().body(new StandardResponse<>(apiResponse));
	        }

	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message(messageResponse.getMessage())
	                .status(HttpStatus.OK)
	                .data(null)
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(apiResponse));

	    } catch (Exception e) {
	        e.printStackTrace();
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Internal Server Error")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(apiResponse));
	    }
	}

	
	@PostMapping("/public/validateOtp")
	public ResponseEntity<?> validateOtp(@RequestBody OtpVerificationRequest request) {
	    try {
	        MessageResponse response = authService.validateOtp(request.getEmail(), request.getOtp());

	        if (response.getMessage().startsWith("Error")) {
	            ApiResponse<Object> apiResponse = ApiResponse.builder()
	                    .message(response.getMessage())
	                    .status(HttpStatus.BAD_REQUEST)
	                    .error("OTP validation failed")
	                    .build();
	            return ResponseEntity.badRequest().body(new StandardResponse<>(apiResponse));
	        }

	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message(response.getMessage())
	                .status(HttpStatus.OK)
	                .error(null)
	                .data(null) // You can return additional data here if needed
	                .build();

	        return ResponseEntity.ok(new StandardResponse<>(apiResponse));
	    } catch (Exception e) {
	        e.printStackTrace();
	        ApiResponse<Object> apiResponse = ApiResponse.builder()
	                .message("Internal Server Error")
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .error(e.getMessage())
	                .build();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new StandardResponse<>(apiResponse));
	    }
	}



}
