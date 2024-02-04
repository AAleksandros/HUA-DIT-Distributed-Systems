package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blooddonationsystem.model.entity.User;

import java.util.Optional;

public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    Optional<Secretary> findByEmail(String email);
    Optional<Secretary> findByUser(User user);

}
