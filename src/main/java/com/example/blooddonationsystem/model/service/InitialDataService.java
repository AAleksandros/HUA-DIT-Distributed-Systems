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
import java.util.Optional;
import java.util.Set;



@Service
public class InitialDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretaryRepository secretaryRepository;

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
        // Check and create an admin user if not exists
        Optional<User> adminUserOpt = userRepository.findByEmail("admin@example.com");
        if (adminUserOpt.isEmpty()) {
            User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminpass"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            admin.setRoles(adminRoles);
            userRepository.save(admin);
        }

        // Check and create a secretary user if not exists
        Optional<User> secretaryUserOpt = userRepository.findByEmail("secretary@example.com");
        if (secretaryUserOpt.isEmpty()) {
            User secretaryUser = new User("secretary", "secretary@example.com", passwordEncoder.encode("secretpass"));
            Set<Role> secretaryRoles = new HashSet<>();
            secretaryRoles.add(roleRepository.findByName("ROLE_SECRETARY").orElseThrow());
            secretaryUser.setRoles(secretaryRoles);
            secretaryUser = userRepository.save(secretaryUser); // Save to get the generated ID

            // Create and link the Secretary entity
            Secretary secretaryDetails = new Secretary();
            secretaryDetails.setFirstName("Maria");
            secretaryDetails.setLastName("Papadopoulou");
            secretaryDetails.setUser(secretaryUser); // Link the User entity
            secretaryRepository.save(secretaryDetails);
        }
    }

    @PostConstruct
    public void initializeData() {
        createRoles();
        createInitialUsers();

    }
}
