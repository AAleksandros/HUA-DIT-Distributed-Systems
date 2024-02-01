package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> { // Ensure the correct ID type is used here, Long instead of Integer if your Citizen ID is of type Long
    Optional<Citizen> findByEmail(String email);

    // Custom query to fetch all citizens with their donation applications
    @Query("SELECT c FROM Citizen c LEFT JOIN FETCH c.donationApplication")
    List<Citizen> findAllWithDonationApplication();


}
