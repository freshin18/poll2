package com.ty.poll.service;
import com.ty.poll.repository.UserRepository;
import com.ty.poll.model.User;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 1. Constructor Injection: This tells Spring to find the UserRepository and plug it in here
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login Logic
    public User loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        // Check if user exists AND password matches
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user; // Login success!
            }
        }
        return null; // Login failed
    }

    // Optional: Register Logic
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }
}