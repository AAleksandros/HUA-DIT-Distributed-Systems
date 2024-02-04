package com.example.blooddonationsystem.model.dao;

import com.example.blooddonationsystem.model.entity.Secretary;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SecretaryDAOImpl implements SecretaryDAO {

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Override
    public List<Secretary> getAllSecretaries() {
        return secretaryRepository.findAll();
    }

    @Override
    public Secretary getSecretaryById(Long id) {
        return secretaryRepository.findById(id).orElse(null);
    }

    @Override
    public Secretary saveOrUpdateSecretary(Secretary secretary) {
        return secretaryRepository.save(secretary);
    }

    @Override
    public void deleteSecretary(Long id) {
        secretaryRepository.deleteById(id);
    }

    // Implement any other operations as needed
}
