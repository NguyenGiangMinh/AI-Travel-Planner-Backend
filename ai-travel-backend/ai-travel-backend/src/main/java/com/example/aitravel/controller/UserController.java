package com.example.aitravel.controller;

import com.example.aitravel.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Unauthenticated");
        }

        User user = (User) authentication.getPrincipal();
        user.setPassword(null); // không trả password
        return ResponseEntity.ok(user);
    }
}