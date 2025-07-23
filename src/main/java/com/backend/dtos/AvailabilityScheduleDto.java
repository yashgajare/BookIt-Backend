package com.backend.dtos;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.backend.enums.DayOfWeekEnum;

import lombok.Data;

@Data
public class AvailabilityScheduleDto {

    private Long providerId;
    private List<DayAvailability> availability;

    @Data
    public static class DayAvailability {
        private DayOfWeekEnum day;          
        private LocalTime startTime;    
        private LocalTime endTime;      
        private boolean available;     

    }
}
