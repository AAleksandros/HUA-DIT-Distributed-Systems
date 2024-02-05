package com.example.blooddonationsystem.model.config;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.entity.DonationApplication;
import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.entity.BloodDonation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Citizen.class);
        config.exposeIdsFor(DonationApplication.class);
        config.exposeIdsFor(Secretary.class);
        config.exposeIdsFor(BloodDonation.class);
    }
}
