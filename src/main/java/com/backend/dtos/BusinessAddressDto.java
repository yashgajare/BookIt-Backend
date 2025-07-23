package com.backend.dtos;

import lombok.Data;

@Data
public class BusinessAddressDto {

	private Long providerId;
	private String AddressLine1;
	private String AddressLine2;
	private String city;
	private String state;
	private String pincode;
	
}
