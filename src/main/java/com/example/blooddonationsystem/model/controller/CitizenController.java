package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.BloodDonation;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.BloodDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;


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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "register";
    }

    @PostMapping("/register")
    public String registerCitizen(@ModelAttribute("citizen") Citizen citizen, RedirectAttributes redirectAttributes) {
        String encodedPassword = passwordEncoder.encode(citizen.getPassword());
        citizen.setPassword(encodedPassword);
        Citizen savedCitizen = citizenRepository.save(citizen);
        redirectAttributes.addAttribute("id", savedCitizen.getId());
        return "redirect:/citizens/success";
    }

    @GetMapping("/success")
    public String registrationSuccess(@RequestParam("id") Integer citizenId, Model model) {
        Citizen citizen = citizenRepository.findById(citizenId).orElse(null);
        model.addAttribute("citizen", citizen);
        model.addAttribute("message", "Registration successful!");
        return "registration_successful";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam("id") Integer citizenId) {
        Citizen citizen = citizenRepository.findById(citizenId).orElse(null);
        if (citizen == null || !hasRole("ROLE_USER")) {
            return "redirect:/login";
        }
        model.addAttribute("citizen", citizen);
        return "dashboard";
    }

    private boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }



    @GetMapping("/application")
    public String showApplicationForm(Model model) {
        model.addAttribute("donationApplication", new BloodDonation());
        return "donation_application";
    }

    @PostMapping("/apply-donation")
    public String applyForDonation(@ModelAttribute("donationApplication") BloodDonation application) {
        // Process the application
        // Save the application to the database (assuming you have a repository for this)
        return "redirect:/citizens/dashboard";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, @RequestParam("id") Integer citizenId) {
        Citizen citizen = citizenRepository.findById(citizenId).orElse(null);
        model.addAttribute("citizen", citizen);
        return "profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("citizen") Citizen updatedCitizen) {
        Citizen existingCitizen = citizenRepository.findById(updatedCitizen.getId()).orElse(null);
        if (existingCitizen != null) {
            if (updatedCitizen.getPassword() != null && !updatedCitizen.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(updatedCitizen.getPassword());
                existingCitizen.setPassword(encodedPassword);
            }
            // Update other fields of existingCitizen as needed
            citizenRepository.save(existingCitizen);
        }
        return "redirect:/citizens/dashboard?id=" + existingCitizen.getId();
    }

    @GetMapping("/my-donations")
    public String viewMyDonations(Model model, @RequestParam("id") Integer citizenId) {
        List<BloodDonation> donations = bloodDonationRepository.findDonationsByCitizenId(citizenId);
        model.addAttribute("donations", donations);
        return "my_donations";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginCitizen(@RequestParam Integer id, Model model) {
        Citizen citizen = citizenRepository.findById(id).orElse(null);
        if (citizen != null) {
            model.addAttribute("citizen", citizen);
            return "redirect:/citizens/dashboard?id=" + citizen.getId();
        } else {
            model.addAttribute("error", "No citizen found with that ID");
            return "login";
        }
    }
}
