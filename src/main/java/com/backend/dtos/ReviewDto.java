package com.backend.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReviewDto {
	
	private String customerName;
	private Integer rating;
	private LocalDateTime timeStamp;
	private String description;
}
