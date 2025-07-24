package com.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    @Size(max = 500, message = "Review text cannot exceed 500 characters")
    private String reviewText;

    private LocalDateTime createdAt;

    @NotNull(message = "Customer is required")
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @NotNull(message = "Provider is required")
    @ManyToOne
    @JoinColumn(name="provider_id")
    private ServiceProvider provider;

    @NotNull(message = "Service is required")
    @ManyToOne
    @JoinColumn(name="service_id")
    private ServiceOffered service;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
