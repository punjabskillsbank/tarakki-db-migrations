package com.tarakki.workspace.service;

import com.tarakki.workspace.entity.User;
import com.tarakki.workspace.entity.UserRole;
import com.tarakki.workspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

   
    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

   
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

   
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

   
    public List<User> getActiveUsersByRole(UserRole role) {
        return userRepository.findByRoleAndIsActiveTrue(role);
    }

   
    public List<User> searchActiveUsersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllActiveUsers();
        }
        return userRepository.findActiveUsersByNameContaining(name.trim());
    }

   
    public boolean isUserActiveById(UUID userId) {
        return userRepository.findById(userId)
                .map(User::getIsActive)
                .orElse(false);
    }

   
    public long getUserCount() {
        return userRepository.count();
    }

   
    public long getActiveUserCount() {
        return userRepository.findByIsActiveTrue().size();
    }
}
