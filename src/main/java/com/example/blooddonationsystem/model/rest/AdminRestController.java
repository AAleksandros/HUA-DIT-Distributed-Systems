package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.payload.request.UserEditRequest;
import com.example.blooddonationsystem.model.payload.response.MessageResponse;
import com.example.blooddonationsystem.model.payload.response.UserDetailsResponse;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import com.example.blooddonationsystem.model.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Autowired
    private CitizenRepository citizenRepository;


    // Add a new user by admin
    @PostMapping("/users/add")
    public ResponseEntity<?> addUser(@RequestBody UserEditRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(userRequest.getUsername(),
                userRequest.getEmail(),
                passwordEncoder.encode(userRequest.getPassword()));

        Set<Role> roles = convertRoleNamesToRoles(userRequest.getRoles());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        // Check roles and create Secretary or Citizen as needed with default values for Citizen
        if (userRequest.getRoles().contains("ROLE_SECRETARY")) {
            Secretary secretary = new Secretary();
            secretary.setUser(savedUser);
            secretaryRepository.save(secretary);
        } else if (userRequest.getRoles().contains("ROLE_CITIZEN")) {
            Citizen citizen = new Citizen();
            citizen.setUser(savedUser);
            citizen.setFirstName("Please update");
            citizen.setLastName("Please update");
            citizen.setBloodType("Please update");
            citizen.setPhoneNumber("Please update");
            citizen.setArea("Please update");
            citizen.setAge(0);
            citizenRepository.save(citizen);
        }

        return ResponseEntity.ok(new MessageResponse("User added successfully by admin."));
    }

    // Update an existing user by admin
    @PutMapping("/users/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserEditRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        // Consider if updating the username is necessary
        user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        Set<Role> roles = convertRoleNamesToRoles(userRequest.getRoles());
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User updated successfully by admin."));
    }

    // Delete a user by admin
    @DeleteMapping("/users/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userDetailsServiceImpl.deleteUserAndAssociations(userId);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully."));
    }

    // Assign roles to a user
    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<?> assignRolesToUser(@PathVariable Long userId, @RequestBody Set<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        Set<Role> roles = convertRoleNamesToRoles(roleNames);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Roles updated successfully for user."));
    }

    // Get all users with their roles
    @GetMapping("/users/get-all-user-details")
    public ResponseEntity<List<UserDetailsResponse>> getAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        List<UserDetailsResponse> userDetailsResponses = users.stream().map(user -> {
            Set<String> roles = user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet());
            return new UserDetailsResponse(user.getId(), user.getUsername(), user.getEmail(), roles);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userDetailsResponses);
    }

    // Helper method to convert role names to roles
    private Set<Role> convertRoleNamesToRoles(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        roleNames.forEach(roleName -> {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " is not found."));
            roles.add(role);
        });
        return roles;
    }
}