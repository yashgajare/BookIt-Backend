package com.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.AvailabilitySchedule;

public interface AvailabilityScheduleRepository extends JpaRepository<AvailabilitySchedule, Long>{

}
