package com.example.aitravel.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    private String displayName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private Double avgBudget;

    @Column(columnDefinition = "json")
    private String preferences;

    private boolean twoFaEnabled = false;
}