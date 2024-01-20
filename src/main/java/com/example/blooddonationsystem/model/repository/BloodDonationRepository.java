package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BloodDonationRepository extends JpaRepository<BloodDonation, Integer> {
}
