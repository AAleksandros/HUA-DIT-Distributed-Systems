package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.*;
import com.example.blooddonationsystem.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private DonationApplicationRepository donationApplicationRepository;

    // Create roles
    public void createRoles() {
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
    public void createInitialUsers() {
        Optional<User> adminUserOpt = userRepository.findByEmail("admin@example.com");
        if (adminUserOpt.isEmpty()) {
            User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminpass"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            admin.setRoles(adminRoles);
            userRepository.save(admin);
        }

        Optional<User> secretaryUserOpt = userRepository.findByEmail("secretary@example.com");
        if (secretaryUserOpt.isEmpty()) {
            User secretaryUser = new User("secretary", "secretary@example.com", passwordEncoder.encode("secretpass"));
            Set<Role> secretaryRoles = new HashSet<>();
            secretaryRoles.add(roleRepository.findByName("ROLE_SECRETARY").orElseThrow());
            secretaryUser.setRoles(secretaryRoles);
            secretaryUser = userRepository.save(secretaryUser);

            Secretary secretaryDetails = new Secretary();
            secretaryDetails.setUser(secretaryUser);
            secretaryRepository.save(secretaryDetails);
        }
    }

    public void createUserWithCitizenRoleAndDonationApplication() {
        Optional<User> testUserOpt = userRepository.findByEmail("aleksandrosa20@gmail.com");
        Secretary processedBySecretary = secretaryRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Secretary with ID 1 not found"));

        if (testUserOpt.isEmpty()) {
            User testUser = new User("scheduletest", "aleksandrosa20@gmail.com", passwordEncoder.encode("pass123"));
            Set<Role> citizenRole = new HashSet<>();
            citizenRole.add(roleRepository.findByName("ROLE_CITIZEN").orElseThrow());
            testUser.setRoles(citizenRole);
            testUser = userRepository.save(testUser);

            Citizen testCitizen = new Citizen();
            testCitizen.setUser(testUser);
            testCitizen.setFirstName("Alex");
            testCitizen.setLastName("Alex");
            testCitizen.setArea("Kratiko Hospital");
            citizenRepository.save(testCitizen);

            // Create DonationApplication with specified criteria
            DonationApplication donationApplication = new DonationApplication();
            donationApplication.setCitizen(testCitizen);
            donationApplication.setHasDrugUse(true);
            donationApplication.setHasRecentProcedures(true);
            donationApplication.setHasRiskBehavior(true);
            donationApplication.setHasTattoosOrPiercings(true);
            donationApplication.setHasTravelToRiskAreas(true);
            donationApplication.setHasAIDS(true);
            donationApplication.setFreeOfInfections(true);
            donationApplication.setRecentlyPregnant(true);
            donationApplication.setBreastfeeding(true);

            donationApplication.setStatus(DonationApplication.ApplicationStatus.valueOf("APPROVED"));
            LocalDate processedDate = LocalDate.parse("01.01.2023", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            LocalTime processedTime = LocalTime.of(0, 0, 0, 0); // Adjust the time as needed
            LocalDateTime processedAt = LocalDateTime.of(processedDate, processedTime);
            donationApplication.setProcessedAt(processedAt);
            donationApplication.setProcessedBy(processedBySecretary);

            donationApplicationRepository.save(donationApplication);
        }
    }
}
