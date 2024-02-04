/*package com.example.blooddonationsystem.model.controller;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("citizen", new Citizen());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("citizen") Citizen citizen, BindingResult bindingResult, Model model) {
        try {
            userService.registerCitizenAsUser(citizen);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
        return "redirect:/registration-successful";
    }

    @GetMapping("/registration-successful")
    public String registrationSuccessful() {
        return "registration_successful";
    }
}


 */