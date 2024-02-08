package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Secretary;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

@Hidden
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {

    @Query("SELECT s FROM Secretary s WHERE s.user.email = :email")
    Optional<Secretary> findByUserEmailIgnoreCase(@Param("email") String email);

    Optional<Secretary> findByUser(User user);
}
