package com.backend.dtos;

import java.time.LocalTime;

import com.backend.enums.DayOfWeekEnum;

import lombok.Data;

@Data
public class DayAvailabilityDto {

	private String day;          
    private LocalTime startTime;    
    private LocalTime endTime;      
    private boolean available;  
	
}
