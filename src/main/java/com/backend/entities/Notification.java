package com.backend.entities;

import com.backend.enums.NotificationType;
import com.backend.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @NotNull(message = "Notification type is required")
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    
    @NotBlank(message = "Message content is required")
    private String message;

    private boolean isRead = false;

    @NotNull(message = "Creation time is required")
    private LocalDateTime createdAt;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "User type is required")
    @Enumerated(EnumType.STRING)
    private UserType userType; // CUSTOMER, PROVIDER
}
