package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DonationApplicationService {

    @Autowired
    private DonationApplicationRepository donationApplicationRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    public DonationApplication createApplication(DonationApplication application) {
        return donationApplicationRepository.save(application);
    }

    public Optional<DonationApplication> getApplicationById(Long applicationId) {
        return donationApplicationRepository.findById(applicationId);
    }

    public DonationApplication updateApplicationStatus(Long applicationId, DonationApplication.ApplicationStatus status) {
        DonationApplication application = getApplicationById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + applicationId));
        application.setStatus(status);
        return donationApplicationRepository.save(application);
    }
    public List<DonationApplication> findApplicationsByEmail(String email) {
        Optional<Citizen> citizen = citizenRepository.findByEmail(email);
        if (citizen.isPresent()) {
            // Convert Integer ID to Long
            Long citizenId = citizen.get().getId().longValue();
            return donationApplicationRepository.findByCitizenId(citizenId);
        }
        return Collections.emptyList();
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

    // Additional methods as needed
}
