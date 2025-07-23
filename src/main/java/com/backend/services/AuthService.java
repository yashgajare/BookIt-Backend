package com.backend.services;

import com.backend.dtos.MessageResponse;
import com.backend.security.request.LoginRequest;
import com.backend.security.request.SignupRequest;
import com.backend.security.response.LoginResponse;

public interface AuthService {

	public MessageResponse registerUser(SignupRequest signupRequest);
	
	public MessageResponse generateOtpAndSend(String email);

	MessageResponse validateOtp(String email, String otp);

	LoginResponse loginUser(LoginRequest loginRequest) throws Exception;
}
