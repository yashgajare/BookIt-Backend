package com.backend.services;

import com.backend.dtos.MessageResponse;
import com.backend.security.request.SignupRequest;

public interface AuthService {

	public MessageResponse registerUser(SignupRequest signupRequest);
	
	public void generateOtp(String email);
	
}
