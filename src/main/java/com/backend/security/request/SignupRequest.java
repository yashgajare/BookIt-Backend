package com.backend.security.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

	private String fullname;
	private String email;
	private String password;
}
