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
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    @Query("SELECT c FROM Citizen c WHERE LOWER(c.user.email) = LOWER(:email)")
    Optional<Citizen> findByUserEmailIgnoreCase(@Param("email") String email);

    @Query("SELECT c FROM Citizen c LEFT JOIN FETCH c.donationApplication da WHERE c.id = :citizenId")
    Optional<Citizen> findByIdWithDonationApplication(@Param("citizenId") Long citizenId);

    @Query("SELECT c FROM Citizen c LEFT JOIN FETCH c.user u WHERE u.id = :userId")
    Optional<Citizen> findByUserId(Long userId);

}