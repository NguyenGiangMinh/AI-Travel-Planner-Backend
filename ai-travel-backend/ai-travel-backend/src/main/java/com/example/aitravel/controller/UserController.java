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

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<?> uploadAvatar(Authentication authentication, @PathVariable Long userId, @RequestParam("file")MultipartFile file) {
        //Kiem tra bao mat
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthenticated");
        }

        User user = (User) authentication.getPrincipal();

        //Kiem tra nguoi danh nhap co phai la chu tai khoan khong
        if (!user.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this user's avatar.");
        }

        try {
            User updateUser = userService.updateAvatar(userId, file);
            return ResponseEntity.ok(updateUser.getAvatarUrl());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}