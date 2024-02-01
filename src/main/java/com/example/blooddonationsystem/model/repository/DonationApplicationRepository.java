package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface DonationApplicationRepository extends JpaRepository<DonationApplication, Long> {
    List<DonationApplication> findByCitizenId(Long citizenId);

    DonationApplication findTopByCitizenIdAndStatusOrderByApprovalDateDesc(Long citizenId, DonationApplication.ApplicationStatus status);

    // Example method to find applications based on health history criteria now included in DonationApplication
    @Query("SELECT da FROM DonationApplication da WHERE da.citizen.id = :citizenId AND da.isFreeOfInfections = true")
    List<DonationApplication> findByCitizenIdAndHealthCriteria(Long citizenId);
}
