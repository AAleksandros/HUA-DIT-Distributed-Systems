package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
}
