package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Secretary;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

@Hidden
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    Optional<Secretary> findByEmailIgnoreCase(String email);
    Optional<Secretary> findByUser(User user);

}
