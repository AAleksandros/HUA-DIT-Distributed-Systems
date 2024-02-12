package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.payload.response.DonationApplicationResponseDTO;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationApplicationService {

    @Autowired
    private DonationApplicationRepository donationApplicationRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;
    public DonationApplication createApplication(DonationApplication application) {
        return donationApplicationRepository.save(application);
    }

    public Optional<DonationApplication> getApplicationById(Long applicationId) {
        return donationApplicationRepository.findById(applicationId);
    }

    @Transactional
    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status, Optional<String> optionalRejectionReason) {
        DonationApplication application = donationApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = getEmailFromAuthentication(authentication);

        Secretary secretary = secretaryRepository.findByUserEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Secretary not found with email: " + email));

        application.setStatus(status);
        application.setProcessedBy(secretary);
        application.setProcessedAt(LocalDateTime.now());


        if (status == DonationApplication.ApplicationStatus.REJECTED) {
            application.setRejectionReason(optionalRejectionReason.orElse(null));
        } else {
            application.setRejectionReason(null);
        }

        return donationApplicationRepository.save(application);
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getEmail();
        } else {
            throw new RuntimeException("Authentication principal does not contain email information.");
        }
    }


    public List<DonationApplicationResponseDTO> findApplicationsByCitizenId(Long citizenId) {
        List<DonationApplication> applications = donationApplicationRepository.findByCitizenId(citizenId);
        return applications.stream()
                .map(application -> new DonationApplicationResponseDTO(application.getId(), application.getStatus().name(),application.getCreatedAt(),
                        application.getCitizen().getId()))
                .collect(Collectors.toList());
    }



    public List<DonationApplication> findAllApplications() {
        return donationApplicationRepository.findAll();
    }

    public void deleteApplication(Long applicationId) {
        donationApplicationRepository.deleteById(applicationId);
    }
    public boolean canCitizenApply(Long citizenId) {
        List<DonationApplication> applications = donationApplicationRepository.findByCitizenId(citizenId);
        return applications.stream().noneMatch(app ->
                app.getStatus() == DonationApplication.ApplicationStatus.PENDING ||
                        app.getStatus() == DonationApplication.ApplicationStatus.APPROVED);
    }
    public List<DonationApplication> findAllApplicationsWithDetails() {
        return donationApplicationRepository.findAllWithDetails();
    }

    public List<DonationApplication> findApplicationsByEmail(String email) {
        Optional<Citizen> citizen = citizenRepository.findByUserEmailIgnoreCase(email);
        return citizen.map(value -> donationApplicationRepository.findByCitizenId(value.getId()))
                .orElseGet(Collections::emptyList);
    }

    public List<DonationApplicationResponseDTO> findApplicationsByStatus(Optional<DonationApplication.ApplicationStatus> status) {
        List<DonationApplication> applications;
        if (status.isPresent()) {
            applications = donationApplicationRepository.findByStatus(status.get());
        } else {
            applications = donationApplicationRepository.findAll();
        }
        return applications.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private DonationApplicationResponseDTO convertToResponseDTO(DonationApplication application) {
        return new DonationApplicationResponseDTO(application.getId(), application.getStatus().name(),application.getCreatedAt(),
                application.getCitizen().getId());
    }


}
