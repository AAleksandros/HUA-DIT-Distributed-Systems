package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.payload.request.DonationApplicationDTO;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import com.example.blooddonationsystem.model.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bloodDonations")
public class BloodDonationRestController {

    @Autowired
    private DonationApplicationService donationApplicationService;

    @Autowired
    private CitizenRepository citizenRepository;

    // Apply for a blood donation
// Apply for a blood donation
    @PostMapping("/apply")
    public ResponseEntity<?> applyForDonation(@Valid @RequestBody DonationApplicationDTO applicationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email;

        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            email = userDetails.getEmail(); // Use getEmail() to retrieve the full email address
        } else {
            throw new RuntimeException("Authentication principal is not an instance of UserDetailsImpl");
        }

        Citizen citizen = citizenRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + email));


        DonationApplication application = new DonationApplication();
        application.setCitizen(citizen);
        application.setHasNoTattoosOrPiercings(applicationDTO.isHasNoTattoosOrPiercings());
        application.setHasNoRecentProcedures(applicationDTO.isHasNoRecentProcedures());
        application.setHasNoTravelToRiskAreas(applicationDTO.isHasNoTravelToRiskAreas());
        application.setHasNoRiskBehavior(applicationDTO.isHasNoRiskBehavior());
        application.setHasNoDrugUse(applicationDTO.isHasNoDrugUse());
        application.setHasAIDS(applicationDTO.isHasAIDS());
        application.setFreeOfInfections(applicationDTO.isFreeOfInfections());
        application.setNotRecentlyPregnant(applicationDTO.isNotRecentlyPregnant());
        application.setNotBreastfeeding(applicationDTO.isNotBreastfeeding());
        // You may need to add additional fields here based on your DonationApplication entity

        donationApplicationService.createApplication(application);

        return ResponseEntity.ok().body("Application submitted successfully!");
    }

    // Get current citizen's applications
    @GetMapping("/my-applications")
    public ResponseEntity<List<DonationApplication>> getCitizenApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new RuntimeException("Citizen not found"));
        List<DonationApplication> applications = donationApplicationService.findApplicationsByCitizenId(citizen.getId());
        return ResponseEntity.ok(applications);
    }

    // View all donation applications (for secretary or admin)
    @GetMapping("/applications")
    public ResponseEntity<List<DonationApplication>> getAllApplications() {
        List<DonationApplication> applications = donationApplicationService.findAllApplications();
        return ResponseEntity.ok(applications);
    }

    // Update a donation application's status (for secretary or admin)
    @PostMapping("/applications/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId, @RequestParam DonationApplication.ApplicationStatus status) {
        donationApplicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok("Application status updated successfully.");
    }
}


