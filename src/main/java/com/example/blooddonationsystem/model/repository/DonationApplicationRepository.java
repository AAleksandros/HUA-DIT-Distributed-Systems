package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Hidden
public interface DonationApplicationRepository extends JpaRepository<DonationApplication, Long> {

    List<DonationApplication> findByCitizenId(Long citizenId);

    List<DonationApplication> findByStatus(DonationApplication.ApplicationStatus status);

    // Find approved applications before a certain date for reminder service
    @Query("SELECT da FROM DonationApplication da WHERE da.status = 'APPROVED' AND da.processedAt < :date")
    List<DonationApplication> findApprovedApplicationsBefore(@Param("date") LocalDateTime date);

}
