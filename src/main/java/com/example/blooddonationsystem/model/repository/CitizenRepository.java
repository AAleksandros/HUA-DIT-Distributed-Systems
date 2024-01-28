package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Optional<Citizen> findByEmail(String email);
}
