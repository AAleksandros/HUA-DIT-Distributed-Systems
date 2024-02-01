package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import com.example.blooddonationsystem.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/citizens")
public class CitizenController {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private DonationApplicationService donationApplicationService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        model.addAttribute("citizen", citizen);
        return "dashboard";
    }


    @GetMapping("/profile")
    public String viewProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        model.addAttribute("citizen", citizen);
        return "profile";
    }
    @GetMapping("/profile/edit")
    public String editProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElse(null);
        if (citizen == null) {
            // Handle the case where the citizen is not found
            return "redirect:/citizens/dashboard";
        }
        model.addAttribute("citizen", citizen);
        return "edit_profile"; // HTML form for editing the citizen's profile
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("citizen") Citizen citizen, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (result.hasErrors()) {
            return "edit_profile"; // Return to the edit form if there are validation errors
        }
        String email = authentication.getName();
        Citizen existingCitizen = citizenRepository.findByEmail(email).orElse(null);
        if (existingCitizen == null) {
            // Handle the case where the citizen is not found
            return "redirect:/citizens/dashboard";
        }
        // Update the existing citizen's information with the form values
        existingCitizen.setFirstName(citizen.getFirstName());
        existingCitizen.setLastName(citizen.getLastName());
        existingCitizen.setPhoneNumber(citizen.getPhoneNumber());
        existingCitizen.setAddress(citizen.getAddress());
        // Add any other fields you wish to update

        citizenRepository.save(existingCitizen);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/citizens/profile";
    }
}
