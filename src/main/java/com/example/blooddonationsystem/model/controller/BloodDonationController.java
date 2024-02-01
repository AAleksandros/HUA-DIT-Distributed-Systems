package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.DonationApplicationRepository;
import com.example.blooddonationsystem.model.service.BloodDonationService;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/bloodDonations")
public class BloodDonationController {

    @Autowired
    private BloodDonationService bloodDonationService;
    @Autowired
    private DonationApplicationRepository donationApplicationRepository;

    @Autowired
    private DonationApplicationService donationApplicationService;

    @Autowired
    private CitizenRepository citizenRepository;

    @GetMapping("/apply")
    public String showApplicationForm(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Citizen not found"));

        boolean canApply = donationApplicationService.canCitizenApply(citizen.getId());
        if (!canApply) {
            return "application_denied"; // Redirect to a "denied" page if the citizen already has a pending or approved application
        }

        model.addAttribute("donationApplication", new DonationApplication());
        return "donor_form";
    }

    @PostMapping("/apply")
    public String applyForDonation(@Valid @ModelAttribute("donationApplication") DonationApplication donationApplication, BindingResult result, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "donor_form";
        }

        try {
            String email = authentication.getName();
            Citizen citizen = citizenRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Citizen not found"));
            donationApplication.setCitizen(citizen);
            donationApplication.setStatus(DonationApplication.ApplicationStatus.PENDING);
            donationApplicationService.createApplication(donationApplication);
            redirectAttributes.addFlashAttribute("successMessage", "Application submitted successfully!");
            return "redirect:/bloodDonations/my-applications";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while submitting your application. Please try again.");
            return "redirect:/bloodDonations/apply";
        }
    }

    @GetMapping("/my-applications")
    public String getCitizenApplications(Model model, Authentication authentication) {
        String email = authentication.getName();
        Citizen citizen = citizenRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Citizen not found"));
        List<DonationApplication> applications = donationApplicationService.findApplicationsByCitizenId(citizen.getId());
        model.addAttribute("applications", applications);
        return "my_applications";
    }

    // Endpoint for the secretary to view all donation applications
    @GetMapping("/applications")
    public String getAllApplications(Model model) {
        List<Citizen> citizensWithApplications = citizenRepository.findAllWithDonationApplication();
        model.addAttribute("citizensWithApplications", citizensWithApplications);
        return "secretary_applications"; // View to list all applications for the secretary
    }


    // Endpoint for the secretary to approve or reject a donation application
    @PostMapping("/applications/{applicationId}/status")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam DonationApplication.ApplicationStatus status, RedirectAttributes redirectAttributes) {
        donationApplicationService.updateApplicationStatus(applicationId, status);
        redirectAttributes.addFlashAttribute("successMessage", "Application status updated successfully.");
        return "redirect:/bloodDonations/applications";
    }
}
