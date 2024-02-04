package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secretary")
public class SecretaryRestController {

    @Autowired
    private DonationApplicationService donationApplicationService;

    // Review all donation applications
    @GetMapping("/review-applications")
    public ResponseEntity<?> viewAllApplications() {
        return ResponseEntity.ok(donationApplicationService.findAllApplicationsWithDetails());
    }

    // Update the status of a donation application
    @PostMapping("/applications/{applicationId}/updateStatus")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId,
                                                     @RequestParam("status") String statusString) {
        try {
            DonationApplication.ApplicationStatus status = DonationApplication.ApplicationStatus.valueOf(statusString.toUpperCase());
            donationApplicationService.updateApplicationStatus(applicationId, status);
            return ResponseEntity.ok("Application status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: Status is invalid.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating application status: " + e.getMessage());
        }
    }

}
