package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Hidden
public interface DonationApplicationRepository extends JpaRepository<DonationApplication, Long> {
    List<DonationApplication> findByCitizenId(Long citizenId);
    List<DonationApplication> findByStatus(DonationApplication.ApplicationStatus status);
}
