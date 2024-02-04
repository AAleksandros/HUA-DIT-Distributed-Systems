package com.example.blooddonationsystem.model.dao;

import com.example.blooddonationsystem.model.entity.Citizen;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CitizenDAOImpl implements CitizenDAO {

    @Autowired
    private CitizenRepository citizenRepository;

    @Override
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    @Override
    public Citizen getCitizenById(Long id) {
        return citizenRepository.findById(id).orElse(null);
    }

    @Override
    public Citizen saveOrUpdateCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public void deleteCitizen(Long id) {
        citizenRepository.deleteById(id);
    }

}
