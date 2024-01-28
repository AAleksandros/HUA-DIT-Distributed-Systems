package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.BloodDonation;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.BloodDonationRepository;
import com.example.blooddonationsystem.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/citizens")
public class CitizenController {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private BloodDonationRepository bloodDonationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        model.addAttribute("citizen", citizen);
        return "dashboard";
    }

    @GetMapping("/application")
    public String showApplicationForm(Model model) {
        model.addAttribute("donationApplication", new BloodDonation());
        return "donation_application";
    }

    @PostMapping("/apply-donation")
    public String applyForDonation(@ModelAttribute("donationApplication") BloodDonation application, Authentication authentication) {
        return "redirect:/citizens/dashboard";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        model.addAttribute("citizen", citizen);
        return "profile";
    }

    @GetMapping("/my-donations")
    public String viewMyDonations(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        if (citizen != null) {
            List<BloodDonation> donations = bloodDonationRepository.findDonationsByCitizenId(citizen.getId());
            model.addAttribute("donations", donations);
        }
        return "my_donations";
    }
}
