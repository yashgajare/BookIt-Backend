package com.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @Size(max = 100, message = "Caption cannot exceed 100 characters")
    private String caption;

    private LocalDateTime uploadedAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;
}
