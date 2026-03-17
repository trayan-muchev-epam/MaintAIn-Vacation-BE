package com.maintain.vacation.service;

import com.maintain.vacation.dto.UserResponse;
import com.maintain.vacation.model.User;
import com.maintain.vacation.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse getUserProfile(String username) {
        User user = findByUsername(username);
        return new UserResponse(user.getId(), user.getUsername(), user.getRemainingVacationDays());
    }
}
