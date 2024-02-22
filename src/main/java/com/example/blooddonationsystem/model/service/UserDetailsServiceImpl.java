package com.example.blooddonationsystem.model.service;

import com.example.blooddonationsystem.model.entity.User;
import com.example.blooddonationsystem.model.repository.CitizenRepository;
import com.example.blooddonationsystem.model.repository.SecretaryRepository;
import com.example.blooddonationsystem.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;


    // Load the user by username
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    // Delete a user and all associated entities
    @Transactional
    public void deleteUserAndAssociations(Long userId) {
        citizenRepository.findByUserId(userId).ifPresent(citizen -> citizenRepository.delete(citizen));

        secretaryRepository.findByUserId(userId).ifPresent(secretary -> secretaryRepository.delete(secretary));

        userRepository.deleteById(userId);
    }

}