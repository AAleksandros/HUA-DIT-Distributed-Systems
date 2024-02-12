package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.config.JwtUtils;
import com.example.blooddonationsystem.model.dao.CitizenDAOImpl;
import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.Role;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.payload.request.LoginRequest;
import com.example.blooddonationsystem.model.payload.request.SignupRequest;
import com.example.blooddonationsystem.model.payload.response.JwtResponse;
import com.example.blooddonationsystem.model.payload.response.MessageResponse;
import com.example.blooddonationsystem.model.repository.RoleRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import com.example.blooddonationsystem.model.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CitizenDAOImpl citizenDAO;


    // Authenticate user
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


    // Register user
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_CITIZEN")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);
        User savedUser = userRepository.save(user);

        Citizen citizen = new Citizen(signUpRequest.getFirstName(), signUpRequest.getLastName(),
                passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(), signUpRequest.getArea(),
                signUpRequest.getBloodType(), signUpRequest.getAge());
        citizen.setUser(savedUser);
        citizenDAO.saveOrUpdateCitizen(citizen);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}