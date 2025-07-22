package com.backend.services;

import com.backend.dtos.MessageResponse;
import com.backend.security.request.SignupRequest;

public interface AuthService {

	public MessageResponse registerUser(SignupRequest signupRequest);
	
	public MessageResponse generateOtpAndSend(String email);

	MessageResponse validateOtp(String email, String otp);
	
}
