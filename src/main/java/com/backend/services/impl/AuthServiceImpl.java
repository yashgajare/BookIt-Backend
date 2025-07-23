package com.backend.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.backend.security.jwt.JwtUtils;
import com.backend.security.request.LoginRequest;
import com.backend.security.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.dtos.MessageResponse;
import com.backend.dtos.VerificationToken;
import com.backend.entities.Customer;
import com.backend.entities.Role;
import com.backend.enums.RoleType;
import com.backend.repositories.CustomerRepository;
import com.backend.repositories.ProviderRepository;
import com.backend.repositories.RoleRepository;
import com.backend.repositories.VerificationTokenRepository;
import com.backend.security.request.SignupRequest;
import com.backend.services.AuthService;
import com.backend.utils.EmailService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private RoleRepository roleRepository;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public LoginResponse loginUser(LoginRequest loginRequest) throws Exception {
		System.out.println(loginRequest.getEmail());
		
		if (!customerRepository.existsByEmail(loginRequest.getEmail()) ){
			throw new Exception("Message : Mail is not registered, register the email" + loginRequest.getEmail());
		}
		Authentication authentication;
		try{
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		}catch (AuthenticationException e){
			Map<String, Object> map = new HashMap<>();

			map.put("message", "Bad Credential");
			map.put("status","false");

			throw new Exception("Bad Credentials, status : false");
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		String jwtToken = jwtUtils.generateJwtFromUsername(userDetails);
		System.out.println("JWT TOKEN: "+ jwtToken);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		LoginResponse response = new LoginResponse(jwtToken, userDetails.getUsername(), roles);

		return response;
	}

	@Override
    public MessageResponse registerUser(SignupRequest signupRequest) {

        // Check if email already exists in customer or provider tables
        if (customerRepository.existsByEmail(signupRequest.getEmail()) || 
            providerRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }
        System.out.println("SignupRequest in service: "+ signupRequest.getEmail());
        // Create Customer entity
        Customer customer = new Customer(
                signupRequest.getFullName(),
                signupRequest.getEmail(),
                signupRequest.getMobileNumber(),
                passwordEncoder.encode(signupRequest.getPassword())
        );
        System.out.println("Customer: "+ customer.getEmail());

        // Set default role
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        System.out.println("Signup strRoles: "+ signupRequest.getRole());
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
        System.out.println("Customer roles: "+ customer.getRoles());
        customerRepository.save(customer);

//        return generateOtpAndSend(signupRequest.getEmail());
        return new MessageResponse("Proceed to Email Verification");
    }

	@Override
	public MessageResponse generateOtpAndSend(String email) {

	    boolean isCustomer = customerRepository.existsByEmail(email);
	    boolean isProvider = providerRepository.existsByEmail(email);

	    if (!isCustomer && !isProvider) {
	        return new MessageResponse("Error: No user found with this email");
	    }

	    String userType = isCustomer ? "CUSTOMER" : "PROVIDER";

	    // Remove any existing OTP for this email
	    verificationTokenRepository.deleteByEmail(email);

	    // Generate 6-digit numeric OTP
	    String otp = UUID.randomUUID().toString();

	    // Set 5-minute expiry
	    Instant expiry = Instant.now().plus(5, ChronoUnit.MINUTES);

	    // Save to database
	    VerificationToken token = new VerificationToken();
	    token.setEmail(email);
	    token.setToken(otp);
	    token.setUserType(userType);
	    token.setExpiryDate(expiry);

	    verificationTokenRepository.save(token);

	    // Send OTP via email 
	   emailService.sendOtpOnMail(email, otp);

	    return new MessageResponse("OTP sent successfully to " + email);
	}
	
	@Override
	public MessageResponse validateOtp(String email, String otp) {
	    Optional<VerificationToken> optionalToken = verificationTokenRepository.findByEmail(email);

	    if (optionalToken.isEmpty()) {
	        return new MessageResponse("Error: No OTP found for this email");
	    }

	    VerificationToken tokenRecord = optionalToken.get();

	    // Check for expiry
	    if (tokenRecord.getExpiryDate().isBefore(Instant.now())) {
	        return new MessageResponse("Error: OTP has expired. Please request a new one.");
	    }

	    // Check if token matches
	    if (!tokenRecord.getToken().equalsIgnoreCase(otp)) {
	        return new MessageResponse("Error: Invalid OTP");
	    }

	    // OTP is valid — delete token (one-time use)
	    verificationTokenRepository.delete(tokenRecord);

	    // Mark user as verified
	    if ("CUSTOMER".equalsIgnoreCase(tokenRecord.getUserType())) {
	        customerRepository.findByEmail(email).ifPresent(c -> {
	            c.setVerified(true);
	            customerRepository.save(c);
	        });
	    } else if ("PROVIDER".equalsIgnoreCase(tokenRecord.getUserType())) {
	    	providerRepository.findByEmail(email).ifPresent(p -> {
	            p.setVerified(true);
	            providerRepository.save(p);
	        });
	    }

	    return new MessageResponse("OTP verified successfully");
	}




}
