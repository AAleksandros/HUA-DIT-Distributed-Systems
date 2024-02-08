package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.Secretary;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Hidden
public interface CitizenRepository extends JpaRepository<Citizen, Long> { // Ensure the correct ID type is used here, Long instead of Integer if your Citizen ID is of type Long
    @Query("SELECT c FROM Citizen c WHERE LOWER(c.user.email) = LOWER(:email)")
    Optional<Citizen> findByUserEmailIgnoreCase(@Param("email") String email);

    @Query("SELECT c FROM Citizen c WHERE LOWER(c.user.username) = LOWER(:username)")
    Optional<Citizen> findByUsernameIgnoreCase(@Param("username") String username);

    // Custom query to fetch all citizens with their donation applications
    @Query("SELECT c FROM Citizen c LEFT JOIN FETCH c.donationApplication")
    List<Citizen> findAllWithDonationApplication();


}