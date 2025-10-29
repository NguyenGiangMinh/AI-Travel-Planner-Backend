package com.example.aitravel.service;

import com.example.aitravel.entity.Place;
import com.example.aitravel.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // Lấy danh sách Most Viewed
    public List<Place> getMostViewedPlaces() {
        return placeRepository.findTop20ByOrderByViewCountDesc();
    }

    // Lấy danh sách Latest
    public List<Place> getLatestPlaces() {
        return placeRepository.findTop20ByOrderByCreatedAtDesc();
    }

    // HÀM QUAN TRỌNG: Tăng lượt xem
    // Hàm này sẽ được gọi mỗi khi người dùng bấm xem chi tiết 1 địa điểm
    @Transactional
    public void incrementViewCount(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        place.setViewCount(place.getViewCount() + 1);
        placeRepository.save(place);
    }

    @Transactional // Dùng @Transactional để tăng view_count
    public Place getPlaceDetails(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa điểm"));

        // Tăng lượt xem mỗi khi có người xem chi tiết
        place.setViewCount(place.getViewCount() + 1);
        return placeRepository.save(place);
    }
}