package com.example.blooddonationsystem.model.dao;

import com.example.blooddonationsystem.model.entity.Secretary;

import java.util.List;

public interface SecretaryDAO {

    List<Secretary> getAllSecretaries();

    Secretary getSecretaryById(Long id);

    Secretary saveOrUpdateSecretary(Secretary secretary);

    void deleteSecretary(Long id);

}
