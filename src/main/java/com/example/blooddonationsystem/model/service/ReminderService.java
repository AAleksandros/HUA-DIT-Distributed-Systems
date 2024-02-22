package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.DonationApplication;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderService {

    @Autowired
    private DonationApplicationService donationApplicationService;

    @Autowired
    private EmailService emailService;

    // Runs every day at 1 AM. Sends reminders to citizens who are eligible to donate blood again
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    // @Scheduled(initialDelay = 0, fixedRate = 300000) // (FOR TESTING) Run the method on execution and every 5 minutes
    public void sendReminders() {
        // Fetch applications that were approved 60 days ago
        LocalDateTime sixtyDaysAgo = LocalDateTime.now().minusDays(60);
        List<DonationApplication> applications = donationApplicationService.findApprovedApplicationsBefore(sixtyDaysAgo);

        for (DonationApplication application : applications) {
            // Construct and send reminder email
            String area = application.getCitizen().getArea();
            String email = application.getCitizen().getUser().getEmail();
            String subject = "Blood Donation Reminder";
            String content = "Dear " + application.getCitizen().getFirstName() + ",\n\n" +
                    "It's been 60 days since your last donation. You're eligible to donate blood again. Please visit " + area + " to proceed with the donation, whenever you are ready.\n\n" +
                    "Best regards,\nHUA Blood Donation Team";

            emailService.sendSimpleMessage(email, subject, content);
        }
    }
}
