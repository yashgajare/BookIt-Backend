package com.backend.dtos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PortfolioImageDto {

	private Long providerId;         
	List<MultipartFile> images;        
    private String caption;  
	
}
