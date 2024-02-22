package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.payload.response.CitizenDetailsResponse;
import com.example.blooddonationsystem.model.payload.response.DonationApplicationResponse;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
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
    private CitizenRepository citizenRepository;

    @Autowired
    private DonationApplicationService donationApplicationService;


    @GetMapping("/review-applications")
    public ResponseEntity<List<DonationApplicationResponse>> viewAllApplications(@RequestParam(value = "status", required = false) String statusString) {
        Optional<DonationApplication.ApplicationStatus> status = Optional.empty();
        if (statusString != null) {
            try {
                status = Optional.of(DonationApplication.ApplicationStatus.valueOf(statusString.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        }

        List<DonationApplicationResponse> applications = donationApplicationService.findApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/getDetails/{citizenId}")
    public ResponseEntity<?> getCitizenDetails(@PathVariable Long citizenId) {
        Optional<Citizen> citizenOpt = citizenRepository.findByIdWithDonationApplication(citizenId);
        if (!citizenOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Citizen citizen = citizenOpt.get();
        CitizenDetailsResponse citizenDetailsResponse = convertToCitizenDetailsResponse(citizen);
        return ResponseEntity.ok(citizenDetailsResponse);
    }

    private CitizenDetailsResponse convertToCitizenDetailsResponse(Citizen citizen) {
        CitizenDetailsResponse response = new CitizenDetailsResponse();
        response.setId(citizen.getId());
        response.setFirstName(citizen.getFirstName());
        response.setLastName(citizen.getLastName());
        response.setEmail(citizen.getUser().getEmail());
        response.setAge(citizen.getAge());

        DonationApplication donationApplication = citizen.getDonationApplication();
        if (donationApplication != null) {
            CitizenDetailsResponse.DonationApplicationDetails donationDetails = new CitizenDetailsResponse.DonationApplicationDetails();
            donationDetails.setId(donationApplication.getId());
            donationDetails.setStatus(donationApplication.getStatus().name());
            donationDetails.setCreatedAt(donationApplication.getCreatedAt());
            donationDetails.setProcessedAt(donationApplication.getProcessedAt());
            donationDetails.setProcessedBySecretary(donationApplication.getProcessedBy() != null ? donationApplication.getProcessedBy().getUser().getUsername() : null);
            donationDetails.setFreeOfInfections(donationApplication.isFreeOfInfections());
            donationDetails.setHasNoTattoosOrPiercings(donationApplication.hasNoTattoosOrPiercings());
            donationDetails.setHasNoRecentProcedures(donationApplication.hasNoRecentProcedures());
            donationDetails.setHasNoTravelToRiskAreas(donationApplication.hasNoTravelToRiskAreas());
            donationDetails.setHasNoRiskBehavior(donationApplication.hasNoRiskBehavior());
            donationDetails.setNotRecentlyPregnant(donationApplication.isRecentlyPregnant());
            donationDetails.setNotBreastfeeding(donationApplication.isBreastfeeding());
            donationDetails.setHasNoDrugUse(donationApplication.isHasDrugUse());
            donationDetails.setHasAIDS(donationApplication.hasAIDS());
            donationDetails.setRejectionReason(donationApplication.getRejectionReason());

            response.setDonationDetails(donationDetails);
        }
        return response;
    }
}
