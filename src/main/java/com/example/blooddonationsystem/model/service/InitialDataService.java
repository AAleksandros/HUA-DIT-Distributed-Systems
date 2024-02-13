package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;



@Service
public class InitialDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create roles
    private void createRoles() {
        String[] roles = {"ROLE_ADMIN", "ROLE_SECRETARY", "ROLE_CITIZEN"};

        for (String roleName : roles) {
            roleRepository.findByName(roleName).orElseGet(() -> {
                Role newRole = new Role(roleName);
                roleRepository.save(newRole);
                return null;
            });
        }
    }

    // Create initial users
    private void createInitialUsers() {
        // Creating an admin user
        userRepository.findByUsername("admin").orElseGet(() -> {
            User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminpass"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            admin.setRoles(adminRoles);
            userRepository.save(admin);
            return null;
        });

        // Creating a secretary user
        userRepository.findByUsername("secretary").orElseGet(() -> {
            User secretary = new User("secretary", "secretary@example.com", passwordEncoder.encode("secretpass"));
            Set<Role> secretaryRoles = new HashSet<>();
            secretaryRoles.add(roleRepository.findByName("ROLE_SECRETARY").orElseThrow());
            secretary.setRoles(secretaryRoles);
            userRepository.save(secretary);
            return null;
        });
    }

    @PostConstruct
    public void initializeData() {
        createRoles();
        createInitialUsers();

    }
}
