package com.backend.dtos;

import java.util.List;
import lombok.Data;

@Data
public class AvailabilityScheduleDto {

    private Long providerId;
    private List<DayAvailabilityDto> availability;

}
