/*package com.example.blooddonationsystem.model.config;

import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialize roles
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Role secretaryRole = roleRepository.findByName("ROLE_SECRETARY")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_SECRETARY")));

        // Check if a default secretary already exists
        Optional<User> existingSecretaryUser = userRepository.findByEmail("secretary@example.com");
        if (!existingSecretaryUser.isPresent()) {
            createDefaultSecretary(secretaryRole);
        }
    }

    private void createDefaultSecretary(Role secretaryRole) {
        if (secretaryRepository.findByEmail("secretary@example.com").isEmpty()) {
            // Create User entity for the secretary
            User user = new User();
            user.setUsername("secretary@example.com");
            user.setEmail("secretary@example.com");
            user.setPassword(passwordEncoder.encode("secretary123"));
            user.setRoles(Collections.singleton(secretaryRole));

            user = userRepository.save(user);

            // Create and save Secretary entity
            Secretary secretary = new Secretary();
            secretary.setFirstName("Default");
            secretary.setLastName("Secretary");
            secretary.setEmail(user.getEmail());
            secretary.setUser(user); // Associate User with Secretary without setting the password again

            secretaryRepository.save(secretary);
        }
    }
}


 */