package com.example.aitravel.repository;

import com.example.aitravel.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    // Tự động tạo query: "SELECT * FROM places ORDER BY view_count DESC LIMIT 20"
    List<Place> findTop20ByOrderByViewCountDesc();

    // Tự động tạo query: "SELECT * FROM places ORDER BY created_at DESC LIMIT 20"
    List<Place> findTop20ByOrderByCreatedAtDesc();
}