/*package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.dao.CitizenDAO;
import com.example.blooddonationsystem.model.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenRestController {

    @Autowired
    private CitizenDAO citizenDao;

    // Get all citizens
    @GetMapping("")
    public ResponseEntity<List<Citizen>> getCitizens() {
        List<Citizen> citizens = citizenDao.getAllCitizens();
        return ResponseEntity.ok(citizens);
    }

    // Save a new citizen or update an existing one
    @PostMapping("")
    public ResponseEntity<Citizen> saveOrUpdateCitizen(@RequestBody Citizen citizen) {
        Citizen savedCitizen = citizenDao.saveOrUpdateCitizen(citizen);
        return ResponseEntity.ok(savedCitizen);
    }

    // Delete a citizen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        citizenDao.deleteCitizen(id);
        return ResponseEntity.ok().build();
    }

}*/

package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.dao.CitizenDAO;
import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.payload.request.CitizenUpdate;
import com.example.blooddonationsystem.model.payload.response.CitizenDetailsResponse;
import com.example.blooddonationsystem.model.payload.response.CitizenMyDetails;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import com.example.blooddonationsystem.model.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizen")
public class CitizenRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping("/update")
    public ResponseEntity<?> updateCitizenInfo(@RequestBody CitizenUpdate citizenUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail;

        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            currentEmail = userDetails.getEmail();
        } else {
            throw new RuntimeException("Authentication principal does not contain the expected details.");
        }

        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + currentEmail));

        // Update fields in the User entity as necessary
        if (citizenUpdateDTO.getPassword() != null && !citizenUpdateDTO.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(citizenUpdateDTO.getPassword()));
        }

        if (citizenUpdateDTO.getEmail() != null && !citizenUpdateDTO.getEmail().trim().isEmpty() && !citizenUpdateDTO.getEmail().equals(currentEmail)) {
            if (userRepository.existsByEmail(citizenUpdateDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email is already in use.");
            }
            user.setEmail(citizenUpdateDTO.getEmail());

            String newUsername = citizenUpdateDTO.getEmail().substring(0, citizenUpdateDTO.getEmail().indexOf('@'));
            user.setUsername(newUsername);
        }

        Citizen citizen = citizenRepository.findByUserEmailIgnoreCase(currentEmail)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + currentEmail));


        // Update Citizen information
        Optional.ofNullable(citizenUpdateDTO.getFirstName()).ifPresent(citizen::setFirstName);
        Optional.ofNullable(citizenUpdateDTO.getLastName()).ifPresent(citizen::setLastName);
        Optional.ofNullable(citizenUpdateDTO.getPhoneNumber()).ifPresent(citizen::setPhoneNumber);
        Optional.ofNullable(citizenUpdateDTO.getArea()).ifPresent(citizen::setArea);
        Optional.ofNullable(citizenUpdateDTO.getBloodType()).ifPresent(citizen::setBloodType);
        Optional.ofNullable(citizenUpdateDTO.getAge()).ifPresent(citizen::setAge);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Update failed due to invalid data.");
        }
        return ResponseEntity.ok().build();
    }




    // Citizens view details of their own profile
    @GetMapping("/get-myDetails")
    public ResponseEntity<CitizenMyDetails> getMyDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email;

        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            email = userDetails.getEmail();
        } else {

            throw new RuntimeException("Authentication principal does not contain the expected details.");
        }

        Citizen citizen = citizenRepository.findByUserEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + email));

        CitizenMyDetails response = convertToCitizenMyDetails(citizen);

        return ResponseEntity.ok(response);
    }


    private CitizenMyDetails convertToCitizenMyDetails(Citizen citizen) {
        CitizenMyDetails response = new CitizenMyDetails();
        response.setId(citizen.getId());
        response.setFirstName(citizen.getFirstName());
        response.setLastName(citizen.getLastName());
        response.setEmail(citizen.getUser().getEmail());
        response.setAge(citizen.getAge());
        response.setArea(citizen.getArea());
        response.setBloodType(citizen.getBloodType());
        response.setPhoneNumber(citizen.getPhoneNumber());

        return response;
    }

}
