package com.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_providers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long providerId;

    @NotBlank(message = "Business name is required")
    @Size(min = 2, max = 100, message = "Business name must be between 2 and 100 characters")
    private String businessName;

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 100, message = "Owner name must be between 2 and 100 characters")
    private String ownerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be a valid 10-digit Indian number")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    private boolean isActive;

    @Column(name = "is_verified")
    private boolean verified = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "avg_rating")
    private Double avgRating;

    @OneToOne(cascade = CascadeType.ALL)
    private Address businessAddress;

    @ManyToOne
    private Category category;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Subcategory> subcategories;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOffered> services = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailabilitySchedule> availabilitySchedules = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioImage> portfolioImages = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingAnalytics> analytics = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 👇 Helper methods to manage bidirectional relationships

    public void addAvailability(AvailabilitySchedule schedule) {
        schedule.setProvider(this);
        this.availabilitySchedules.add(schedule);
    }

    public void addService(ServiceOffered service) {
        service.setProvider(this);
        this.services.add(service);
    }

    public void addPortfolioImage(PortfolioImage image) {
        image.setProvider(this);
        this.portfolioImages.add(image);
    }

    public void addAppointment(Appointment appointment) {
        appointment.setProvider(this);
        this.appointments.add(appointment);
    }

    public void addReview(Review review) {
        review.setProvider(this);
        this.reviews.add(review);
    }

    public void addAnalytics(BookingAnalytics analytics) {
        analytics.setProvider(this);
        this.analytics.add(analytics);
    }
}
