package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional  // Ensure transactional context for this method
    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status, String secretaryEmail) {
        DonationApplication application = getApplicationById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));

        Secretary secretary = secretaryRepository.findByEmail(secretaryEmail)
                .orElseThrow(() -> new RuntimeException("Secretary not found with email: " + secretaryEmail));

        application.setStatus(status);
        application.setProcessedBy(secretary);
        application.setProcessedAt(LocalDateTime.now());

        return donationApplicationRepository.save(application);
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
    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status) {
        DonationApplication application = getApplicationById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));
        application.setStatus(status);
        return donationApplicationRepository.save(application);
    }
    public List<DonationApplication> findApplicationsByEmail(String email) {
        Optional<Citizen> citizen = citizenRepository.findByEmail(email);
        return citizen.map(value -> donationApplicationRepository.findByCitizenId(value.getId()))
                .orElseGet(Collections::emptyList);
    }

    // Additional methods as needed
}
