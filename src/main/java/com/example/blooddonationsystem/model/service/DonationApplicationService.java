package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
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
    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status) {
        DonationApplication application = donationApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = getEmailFromAuthentication(authentication); // Ensure this method gets the full email

        Secretary secretary = secretaryRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Secretary not found with email: " + email));

        application.setStatus(status);
        application.setProcessedBy(secretary);
        application.setProcessedAt(LocalDateTime.now());

        return donationApplicationRepository.save(application);
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getEmail(); // This should return the full email address
        } else {
            // Fallback or error handling if the principal is not an instance of UserDetailsImpl
            throw new RuntimeException("Authentication principal does not contain email information.");
        }
    }




    public List<DonationApplication> findApplicationsByCitizenId(Long citizenId) {
        return donationApplicationRepository.findByCitizenId(citizenId);
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
        Optional<Citizen> citizen = citizenRepository.findByEmailIgnoreCase(email);
        return citizen.map(value -> donationApplicationRepository.findByCitizenId(value.getId()))
                .orElseGet(Collections::emptyList);
    }

    // Additional methods as needed
}
