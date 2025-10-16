package com.example.aitravel.controller;

import com.example.aitravel.entity.User;
import com.example.aitravel.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.login(
                request.get("email"),
                request.get("password")
        ));
    }
}