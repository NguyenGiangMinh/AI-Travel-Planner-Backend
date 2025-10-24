package com.example.aitravel.controller;

import com.example.aitravel.entity.User;
import com.example.aitravel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Unauthenticated");
        }

        User user = (User) authentication.getPrincipal();
        user.setPassword(null); // không trả password
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            Authentication authentication,
            @RequestPart(value = "displayName", required = false) String displayName,
            @RequestPart(value = "email", required = false) String email,
            @RequestPart(value = "password", required = false) String password,
            @RequestPart(value = "avatarUrl", required = false) MultipartFile avatarFile
    ) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthenticated");
        }
        User currentUser = (User) authentication.getPrincipal();

        try {
            User updatedUser = userService.updateUserProfile(currentUser.getId(), displayName, email, password, avatarFile);
            updatedUser.setPassword(null);
            return ResponseEntity.ok(updatedUser);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}