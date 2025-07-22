package com.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendOtpOnMail(String email, String otp) {
	    String subject = "Verify Your Email - OTP Inside";
	    
	    String body = """
	        Hi,

	        Thank you for registering with us!

	        Your One-Time Password (OTP) for email verification is:

	        OTP: %s

	        This OTP is valid for 5 minutes. Please do not share it with anyone.

	        If you did not request this OTP, please ignore this email.

	        Regards,
	        The Appointment Booking Team
	    """.formatted(otp);

	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject(subject);
	    message.setText(body);
	    mailSender.send(message);
	}

	
}
