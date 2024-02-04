/*package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import com.example.blooddonationsystem.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

            return "redirect:/citizens/dashboard";
        }
        model.addAttribute("citizen", citizen);
        return "edit_profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("citizen") Citizen updatedCitizen, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (result.hasErrors()) {
            return "edit_profile";
        }

        try {
            String email = authentication.getName();
            userService.updateCitizenAndUserProfiles(updatedCitizen, email);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the profile.");
        }

        return "redirect:/citizens/profile";
    }

}

 */