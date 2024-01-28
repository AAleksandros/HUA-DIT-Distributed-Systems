package com.example.blooddonationsystem.model.config;

import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}
