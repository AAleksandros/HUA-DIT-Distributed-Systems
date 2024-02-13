package com.example.blooddonationsystem.model.service;


import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.payload.response.DonationApplicationResponseDTO;

import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationApplicationService {

    @Autowired
    private DonationApplicationRepository donationApplicationRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;
    public DonationApplication createApplication(DonationApplication application) {
        return donationApplicationRepository.save(application);
    }

    // Update the status of the application
    @Transactional
    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status, Optional<String> optionalRejectionReason, String secretaryEmail) {
        DonationApplication application = donationApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

        // Find Secretary by email
        Secretary secretary = secretaryRepository.findByUserEmailIgnoreCase(secretaryEmail)
                .orElseThrow(() -> new RuntimeException("Secretary not found with email: " + secretaryEmail));

        String username = secretary.getUser().getUsername();

        application.setStatus(status);
        application.setProcessedBy(secretary);
        application.setProcessedAt(LocalDateTime.now());

        if (status == DonationApplication.ApplicationStatus.REJECTED) {
            application.setRejectionReason(optionalRejectionReason.orElse(null));
        } else {
            application.setRejectionReason(null);
        }

        // Log the processing
        System.out.println("Processing by secretary with username: " + username);

        return donationApplicationRepository.save(application);
    }

    // Get all applications by citizen id
    public List<DonationApplicationResponseDTO> findApplicationsByCitizenId(Long citizenId) {
        List<DonationApplication> applications = donationApplicationRepository.findByCitizenId(citizenId);
        return applications.stream()
                .map(application -> new DonationApplicationResponseDTO(application.getId(), application.getStatus().name(),application.getCreatedAt(),
                        application.getCitizen().getId()))
                .collect(Collectors.toList());
    }


    // Get all applications
    public List<DonationApplication> findAllApplications() {
        return donationApplicationRepository.findAll();
    }

    // Get all applications by status
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

    // Convert to response DTO
    private DonationApplicationResponseDTO convertToResponseDTO(DonationApplication application) {
        return new DonationApplicationResponseDTO(application.getId(), application.getStatus().name(),application.getCreatedAt(),
                application.getCitizen().getId());
    }


}
