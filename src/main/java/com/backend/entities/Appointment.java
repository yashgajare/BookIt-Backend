package com.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import com.backend.enums.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @NotNull(message = "Appointment date and time is required")
    @Future(message = "Appointment date and time must be in the future")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @NotNull(message = "Customer is required")
    @ManyToOne
    private Customer customer;

    @NotNull(message = "Provider is required")
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;

    @NotNull(message = "Service is required")
    @ManyToOne
    private ServiceOffered service;
}
