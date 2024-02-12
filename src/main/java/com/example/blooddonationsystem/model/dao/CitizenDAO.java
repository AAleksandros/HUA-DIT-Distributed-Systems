package com.example.blooddonationsystem.model.dao;

import com.example.blooddonationsystem.model.entity.Citizen;

import java.util.List;

public interface CitizenDAO {

    List<Citizen> getAllCitizens();

    Citizen getCitizenById(Long id);

    Citizen saveOrUpdateCitizen(Citizen citizen);

    void deleteCitizen(Long id);

}
