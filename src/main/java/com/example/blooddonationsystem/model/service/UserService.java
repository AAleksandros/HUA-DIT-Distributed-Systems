package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CitizenRepository citizenRepository;

    @Transactional
    public void registerCitizenAsUser(Citizen citizen) {
        if (userRepository.findByEmail(citizen.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + citizen.getEmail());
        }

        // Encrypt the password
        String encodedPassword = passwordEncoder.encode(citizen.getPassword());

        // User entity
        User user = new User();
        user.setUsername(citizen.getEmail()); // Use email as username
        user.setEmail(citizen.getEmail());
        user.setPassword(encodedPassword); // Use the encoded password

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userRepository.save(user); // Save the User entity

        // Citizen entity
        citizen.setPassword(encodedPassword);
        citizen.setUser(savedUser);
        citizenRepository.save(citizen); // Save the Citizen entity
    }
    @Transactional
    public void updateCitizenAndUserProfiles(Citizen updatedCitizen, String email) {
        Citizen existingCitizen = citizenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        // Update Citizen fields
        existingCitizen.setFirstName(updatedCitizen.getFirstName());
        existingCitizen.setLastName(updatedCitizen.getLastName());
        existingCitizen.setAge(updatedCitizen.getAge());
        existingCitizen.setPhoneNumber(updatedCitizen.getPhoneNumber());
        existingCitizen.setAddress(updatedCitizen.getAddress());
        existingCitizen.setBloodType(updatedCitizen.getBloodType());

        // Update password if provided and different
        if (!updatedCitizen.getPassword().isEmpty() && !passwordEncoder.matches(updatedCitizen.getPassword(), existingCitizen.getPassword())) {
            String encodedPassword = passwordEncoder.encode(updatedCitizen.getPassword());
            existingCitizen.setPassword(encodedPassword);
            existingCitizen.getUser().setPassword(encodedPassword); // Also update the associated User entity
            userRepository.save(existingCitizen.getUser());
        }

        citizenRepository.save(existingCitizen);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


}
