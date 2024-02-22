package com.example.blooddonationsystem.model.repository;

import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;


import java.util.Optional;

@Hidden
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {

    @Query("SELECT s FROM Secretary s WHERE s.user.email = :email")
    Optional<Secretary> findByUserEmailIgnoreCase(@Param("email") String email);

    @Query("SELECT s FROM Secretary s WHERE s.user.username = :username")
    Optional<Secretary> findByUserUsername(@Param("username") String username);

    @Query("SELECT s FROM Secretary s WHERE s.user.id = :userId")
    Optional<Secretary> findByUserId(@Param("userId") Long userId);

}
