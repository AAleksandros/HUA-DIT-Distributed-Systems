package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.BloodDonation;
import com.example.blooddonationsystem.model.repository.BloodDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodDonationService {

    @Autowired
    private BloodDonationRepository bloodDonationRepository;

    public BloodDonation saveBloodDonation(BloodDonation bloodDonation) {
        return bloodDonationRepository.save(bloodDonation);
    }

    public List<BloodDonation> findAllBloodDonations() {
        return bloodDonationRepository.findAll();
    }

    public List<BloodDonation> findDonationsByCitizenId(Integer citizenId) {
        return bloodDonationRepository.findDonationsByCitizenId(citizenId);
    }

    public void deleteBloodDonation(Integer id) {
        bloodDonationRepository.deleteById(id);
    }
}
