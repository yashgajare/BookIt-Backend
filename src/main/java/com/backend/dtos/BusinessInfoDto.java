package com.backend.dtos;

import lombok.Data;

@Data
public class BusinessInfoDto {
	private Long customerId;
	private String ownerName;
	private String businessName;
	private String mobile_number;
	private String email;
}
