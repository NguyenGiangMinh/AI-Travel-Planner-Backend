package com.example.aitravel.service;

import com.example.aitravel.entity.User;
import com.example.aitravel.repository.UserRepository;
import com.example.aitravel.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Map<String, Object> register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());
        return Map.of("token", token, "user", savedUser);
    }

    public Map<String, Object> login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return Map.of("token", token, "user", user);
    }
}