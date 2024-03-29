package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.payload.request.DonationApplication;
import com.example.blooddonationsystem.model.payload.response.DonationApplicationResponse;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import com.example.blooddonationsystem.model.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.blooddonationsystem.model.service.EmailService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/bloodDonations")
public class BloodDonationRestController {

    private static final Logger logger = LoggerFactory.getLogger(BloodDonationRestController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DonationApplicationService donationApplicationService;

    @Autowired
    private CitizenRepository citizenRepository;

    // Apply for a blood donation
    @PostMapping("/apply")
    public ResponseEntity<?> applyForDonation(@Valid @RequestBody DonationApplication applicationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email;

        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            email = userDetails.getEmail();
        } else {
            throw new RuntimeException("Authentication principal is not an instance of UserDetailsImpl");
        }

        Citizen citizen = citizenRepository.findByUserEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found for email: " + email));


        com.example.blooddonationsystem.model.entity.DonationApplication application = new com.example.blooddonationsystem.model.entity.DonationApplication();
        application.setCitizen(citizen);
        application.setHasTattoosOrPiercings(applicationDTO.isHasTattoosOrPiercings());
        application.setHasRecentProcedures(applicationDTO.isHasRecentProcedures());
        application.setHasTravelToRiskAreas(applicationDTO.isHasTravelToRiskAreas());
        application.setHasRiskBehavior(applicationDTO.isHasRiskBehavior());
        application.setHasDrugUse(applicationDTO.isHasDrugUse());
        application.setHasAIDS(applicationDTO.isHasAIDS());
        application.setFreeOfInfections(applicationDTO.isFreeOfInfections());
        application.setRecentlyPregnant(applicationDTO.isRecentlyPregnant());
        application.setBreastfeeding(applicationDTO.isBreastfeeding());

        donationApplicationService.createApplication(application);

        return ResponseEntity.ok().body("Application submitted successfully!");
    }

    // Get current citizen's applications
    @GetMapping("/my-applications")
    public ResponseEntity<List<DonationApplicationResponse>> getMyApplication() {
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

        List<DonationApplicationResponse> applications = donationApplicationService.findApplicationsByCitizenId(citizen.getId());
        return ResponseEntity.ok(applications);
    }


    // Update a donation application's status
    @Secured("ROLE_SECRETARY")
    @PostMapping("/applications/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId,
                                                     @RequestParam com.example.blooddonationsystem.model.entity.DonationApplication.ApplicationStatus status,
                                                     @RequestParam(required = false) String rejectionReason) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String secretaryEmail = "";
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            secretaryEmail = userDetails.getEmail();
        }

        Optional<String> optionalRejectionReason = Optional.ofNullable(rejectionReason);

        com.example.blooddonationsystem.model.entity.DonationApplication updatedApplication = donationApplicationService.updateApplicationStatus(applicationId, status, optionalRejectionReason, secretaryEmail);

        String citizenEmail = updatedApplication.getCitizen().getUser().getEmail();
        String area = updatedApplication.getCitizen().getArea();

        // Prepare the email content based on the status
        String subject = "Application Status Update";
        String content;

        if (status == com.example.blooddonationsystem.model.entity.DonationApplication.ApplicationStatus.APPROVED) {
            content = "Dear " + updatedApplication.getCitizen().getFirstName() + ",\n\n" +
                    "Your application has been approved. Please visit our blood donation center located at " + area + " for further instructions.\n\n" +
                    "Best regards,\nHUA Blood Donation Team";
        } else if (status == com.example.blooddonationsystem.model.entity.DonationApplication.ApplicationStatus.REJECTED) {
            content = "Dear " + updatedApplication.getCitizen().getFirstName() + ",\n\n" +
                    "Unfortunately, your application has been rejected.\n" +
                    "Reason: " + optionalRejectionReason.orElse("Not specified") + "\n\n" +
                    "Best regards,\nHUA Blood Donation Team";
        } else {
            content = "Your application status has been updated to " + status + ".";
        }

        emailService.sendSimpleMessage(citizenEmail, subject, content);

        return ResponseEntity.ok("Application status updated successfully.");
    }
}