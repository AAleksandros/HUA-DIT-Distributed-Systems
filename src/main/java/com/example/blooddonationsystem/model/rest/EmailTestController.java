package com.example.blooddonationsystem.model.rest;

import com.example.blooddonationsystem.model.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-test-email")
    public String sendTestEmail() {
        String recipient = "test@email.com";
        String subject = "Test Email";
        String content = "This is a test email sent from Spring Boot application.";

        emailService.sendSimpleMessage(recipient, subject, content);

        return "Test email sent successfully";
    }
}
