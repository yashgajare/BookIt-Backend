package com.backend.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderDraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long provider_id;
    
    // Step 1 - Business Basic Info
    private String businessName;
    private String email;
    private String mobileNumber;
    private String ownerName;

    // Step 2 - Category and Services
    @ManyToOne
    private Category category;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Subcategory> subcategories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ServiceOffered> services;

    // Step 3 - Location
    @OneToOne(cascade = CascadeType.ALL)
    private Address businessAddress;

    // Step 4 - Availability Schedule
    @OneToMany(cascade = CascadeType.ALL)
    private List<AvailabilitySchedule> availabilitySchedules;

    // Step 5 - Portfolio Images
    @OneToMany(cascade = CascadeType.ALL)
    private List<PortfolioImage> portfolioImages;

    private boolean completed; // final submit flag
}
