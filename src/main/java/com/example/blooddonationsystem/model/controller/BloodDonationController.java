package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.BloodDonation;
import com.example.blooddonationsystem.model.repository.BloodDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blooddonations")
public class   BloodDonationController {
    @Autowired
    private BloodDonationRepository bloodDonationRepository;
    @GetMapping("")
    public String showBloodDonations(Model model){
        List<BloodDonation> blooddonations = bloodDonationRepository.findAll();
        model.addAttribute("blooddonations",blooddonations);
        return "blooddonations";
    }

}
