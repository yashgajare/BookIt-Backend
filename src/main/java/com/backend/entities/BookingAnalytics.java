package com.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Min(value = 0, message = "Total bookings cannot be negative")
    private int totalBookings;

    @Min(value = 0, message = "Total revenue cannot be negative")
    private double totalRevenue;

    @NotNull(message = "Service provider is required")
    @ManyToOne
    private ServiceProvider provider;
}
