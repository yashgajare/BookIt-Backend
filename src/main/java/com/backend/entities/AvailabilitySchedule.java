package com.backend.entities;

import com.backend.enums.DayOfWeekEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Enumerated(EnumType.STRING)
    private DayOfWeekEnum dayOfWeek;

    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Start time must be in HH:mm format")
    private String startTime;

    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "End time must be in HH:mm format")
    private String endTime;

    @NotNull(message = "Provider is required")
    @ManyToOne
    private ServiceProvider provider;
}
