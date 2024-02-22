package com.example.blooddonationsystem.model.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationHelper {

    @Autowired
    private InitialDataService initialDataService;

    @Transactional
    public void initialize() {
        initialDataService.createRoles();
        initialDataService.createInitialUsers();
        initialDataService.createUserWithCitizenRoleAndDonationApplication();
    }
}
