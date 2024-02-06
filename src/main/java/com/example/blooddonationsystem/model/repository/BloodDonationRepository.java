package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.BloodDonation;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface BloodDonationRepository extends JpaRepository<BloodDonation, Integer> {

    @Query("SELECT bd FROM BloodDonation bd JOIN bd.citizens c WHERE c.id = :citizenId")
    List<BloodDonation> findDonationsByCitizenId(Integer citizenId);
}
