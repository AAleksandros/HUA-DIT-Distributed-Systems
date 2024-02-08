package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.payload.response.DonationApplicationResponseDTO;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/secretary")
public class SecretaryRestController {

    @Autowired
    private DonationApplicationService donationApplicationService;

    // Review all donation applications, with optional status filter
    @GetMapping("/review-applications")
    public ResponseEntity<List<DonationApplicationResponseDTO>> viewAllApplications(@RequestParam(value = "status", required = false) String statusString) {
        Optional<DonationApplication.ApplicationStatus> status = Optional.empty();
        if (statusString != null) {
            try {
                status = Optional.of(DonationApplication.ApplicationStatus.valueOf(statusString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }

        List<DonationApplicationResponseDTO> applications = donationApplicationService.findApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }
}
