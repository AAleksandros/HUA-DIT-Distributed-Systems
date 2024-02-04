/*package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.service.DonationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/secretary")
public class SecretaryController {

    @Autowired
    private DonationApplicationService donationApplicationService;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "secretary_dashboard";
    }

    @GetMapping("/review-applications")
    public String viewAllApplications(Model model) {
        model.addAttribute("applications", donationApplicationService.findAllApplications());
        return "review_applications";
    }

    @GetMapping("/applications/{applicationId}/updateStatus")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam("status") DonationApplication.ApplicationStatus status, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming the secretary's email is used as the username

        try {
            donationApplicationService.updateApplicationStatus(applicationId, status, email); // Pass the secretary's email to the service method
            redirectAttributes.addFlashAttribute("successMessage", "Application status updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating application status: " + e.getMessage());
        }
        return "redirect:/secretary/review-applications"; // Make sure this matches your GetMapping path
    }
}


 */