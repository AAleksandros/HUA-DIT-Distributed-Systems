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
import com.example.blooddonationsystem.model.payload.request.CitizenUpdate;
import com.example.blooddonationsystem.model.payload.response.CitizenDetailsResponse;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
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
    private CitizenRepository citizenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CitizenDAO citizenDao;

    @PostMapping("")
    public Citizen saveOrUpdateCitizen(@RequestBody Citizen citizen){
        return citizenDao.saveOrUpdateCitizen(citizen);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCitizenInfo(@RequestBody CitizenUpdate citizenUpdateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Citizen citizen = citizenRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Citizen not found for username: " + username));

        // Check each field for null before updating.
        Optional.ofNullable(citizenUpdateDTO.getFirstName()).ifPresent(citizen::setFirstName);
        Optional.ofNullable(citizenUpdateDTO.getLastName()).ifPresent(citizen::setLastName);
        if (citizenUpdateDTO.getPassword() != null && !citizenUpdateDTO.getPassword().trim().isEmpty()) {
            citizen.setPassword(passwordEncoder.encode(citizenUpdateDTO.getPassword()));
        }
        Optional.ofNullable(citizenUpdateDTO.getEmail()).ifPresent(citizen::setEmail);
        Optional.ofNullable(citizenUpdateDTO.getPhoneNumber()).ifPresent(citizen::setPhoneNumber);
        Optional.ofNullable(citizenUpdateDTO.getArea()).ifPresent(citizen::setArea);
        Optional.ofNullable(citizenUpdateDTO.getBloodType()).ifPresent(citizen::setBloodType);
        Optional.ofNullable(citizenUpdateDTO.getAge()).ifPresent(citizen::setAge);

        try {
            citizenRepository.save(citizen);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Update failed due to invalid data.");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getDetails/{citizenId}")
    public ResponseEntity<?> getCitizenDetails(@PathVariable Long citizenId) {
        Optional<Citizen> citizenOpt = citizenRepository.findByIdWithDonationApplication(citizenId);
        if (!citizenOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Citizen citizen = citizenOpt.get();
        // Optionally, convert to a DTO to avoid exposing sensitive information and to control the JSON structure
        return ResponseEntity.ok(citizen);
    }

}
