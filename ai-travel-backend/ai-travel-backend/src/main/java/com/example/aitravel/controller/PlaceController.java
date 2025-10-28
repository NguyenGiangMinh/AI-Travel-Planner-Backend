package com.example.aitravel.controller;

import com.example.aitravel.entity.Place;
import com.example.aitravel.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places") // Endpoint chung cho địa điểm
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    // API cho Android gọi: "Most Viewed"
    // GET http://.../api/places/most-viewed
    @GetMapping("/most-viewed")
    public ResponseEntity<List<Place>> getMostViewed() {
        return ResponseEntity.ok(placeService.getMostViewedPlaces());
    }

    // API cho Android gọi: "Latest"
    // GET http://.../api/places/latest
    @GetMapping("/latest")
    public ResponseEntity<List<Place>> getLatest() {
        return ResponseEntity.ok(placeService.getLatestPlaces());
    }

    // API này Android sẽ gọi khi người dùng bấm vào xem chi tiết 1 địa điểm
    // (Để tăng lượt xem cho "Most Viewed")
    // POST http://.../api/places/123/view
    @PostMapping("/{id}/view")
    public ResponseEntity<Void> recordView(@PathVariable Long id) {
        placeService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }
}