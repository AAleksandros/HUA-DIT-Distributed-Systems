package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Hidden
public interface DonationApplicationRepository extends JpaRepository<DonationApplication, Long> {
    List<DonationApplication> findByCitizenId(Long citizenId);

    DonationApplication findTopByCitizenIdAndStatusOrderByApprovalDateDesc(Long citizenId, DonationApplication.ApplicationStatus status);
    @Query("SELECT da FROM DonationApplication da JOIN FETCH da.citizen c")
    List<DonationApplication> findAllWithDetails();

    @Query("SELECT da FROM DonationApplication da WHERE da.citizen.id = :citizenId AND da.isFreeOfInfections = true")
    List<DonationApplication> findByCitizenIdAndHealthCriteria(Long citizenId);
}
