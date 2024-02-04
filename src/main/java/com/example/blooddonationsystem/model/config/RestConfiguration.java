package com.example.blooddonationsystem.model.config;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.entity.BloodDonation;
// Make sure that the above entities are present and correctly imported.

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Expose the IDs for each of your entities
        config.exposeIdsFor(Citizen.class, DonationApplication.class, Secretary.class, BloodDonation.class);
        // Add any other entities you need to expose IDs for.

        // Configure CORS if needed
        cors.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }
}
