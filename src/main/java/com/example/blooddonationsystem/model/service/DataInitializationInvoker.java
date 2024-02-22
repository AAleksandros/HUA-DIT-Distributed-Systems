package com.example.blooddonationsystem.model.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializationInvoker {

    @Autowired
    private DataInitializationHelper dataInitializationHelper;

    @PostConstruct
    public void initialize() {
        dataInitializationHelper.initialize();
    }
}
