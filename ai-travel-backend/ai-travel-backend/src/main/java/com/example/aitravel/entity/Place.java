package com.example.aitravel.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "places")
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String vicinity;

    @Column(name = "image_url", length = 1024)
    private String imageUrl; // URL đầy đủ đến ảnh

    @Column(name = "view_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer viewCount = 0; // Cho Most Viewed

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt; // Cho Latest

    // Thêm các trường khác nếu cần (description, lat, lng...)
}