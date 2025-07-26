package com.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOffered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @NotBlank(message = "Service name is required")
    @Size(min = 2, max = 100, message = "Service name must be between 2 and 100 characters")
    private String serviceName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Active status must be specified")
    private Boolean isActive;

    @NotNull(message = "Duration is required")
    @Min(value = 5, message = "Minimum service duration is 5 minutes")
    private Integer duration; // in minutes

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Provider is required")
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
