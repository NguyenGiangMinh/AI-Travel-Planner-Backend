package com.example.aitravel.service;

import com.example.aitravel.entity.Itinerary;
import com.example.aitravel.entity.User;
import com.example.aitravel.entity.InviteLinkResponse; // Import này đã có
import com.example.aitravel.repository.ItineraryRepository;
import com.example.aitravel.entity.ItineraryCollaborator;
import com.example.aitravel.repository.ItineraryCollaboratorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class ItineraryService { // <-- ĐÂY LÀ NỘI DUNG ĐÚNG
    private final ItineraryRepository itineraryRepository;
    private final ItineraryCollaboratorRepository collaboratorRepository;

    public ItineraryService(ItineraryRepository itineraryRepository,
                            ItineraryCollaboratorRepository collaboratorRepository) {
        this.itineraryRepository = itineraryRepository;
        this.collaboratorRepository = collaboratorRepository;
    }

    // Hàm này đã đúng
    public List<Itinerary> getUserItineraries(Long userId) {
        List<Itinerary> ownedItineraries = itineraryRepository.findByOwnerId(userId);
        List<ItineraryCollaborator> collaborations = collaboratorRepository.findByUserId(userId);

        List<Itinerary> collaboratedItineraries = collaborations.stream()
                .map(collab -> itineraryRepository.findById(collab.getItineraryId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        List<Itinerary> allItineraries = new ArrayList<>(ownedItineraries);
        allItineraries.addAll(collaboratedItineraries);

        return allItineraries;
    }

    // Hàm này đã đúng
    public Itinerary createItinerary(Itinerary itinerary) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        itinerary.setOwner(currentUser);
        itinerary.setCreatedAt(java.time.LocalDateTime.now());
        itinerary.setUpdatedAt(java.time.LocalDateTime.now());

        Itinerary savedItinerary = itineraryRepository.save(itinerary);

        // Tự động thêm chủ sở hữu làm collaborator
        ItineraryCollaborator ownerAsCollaborator = new ItineraryCollaborator();
        ownerAsCollaborator.setItineraryId(savedItinerary.getId());
        ownerAsCollaborator.setUserId(currentUser.getId());
        ownerAsCollaborator.setRole(ItineraryCollaborator.Role.EDITOR);
        ownerAsCollaborator.setInvitedBy(currentUser.getId());

        collaboratorRepository.save(ownerAsCollaborator);

        return savedItinerary;
    }

    // Hàm này đã sửa lỗi logic (dùng existsBy)
    public Itinerary getItineraryById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Itinerary itinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));

        boolean isOwner = itinerary.getOwner().getId().equals(currentUser.getId());

        // Bạn cần thêm hàm 'existsByItineraryIdAndUserId' vào Repository
        boolean isCollaborator = collaboratorRepository
                .existsByItineraryIdAndUserId(id, currentUser.getId());

        if (isOwner || isCollaborator) {
            return itinerary;
        } else {
            throw new RuntimeException("Access Denied to itinerary " + id);
        }
    }

    // Hàm này đã đúng
    public InviteLinkResponse generateInviteLink(Long itineraryId, User currentUser) {
        Itinerary itinerary = this.getItineraryById(itineraryId);
        String link = "aitravelapp://join?tripId=" + itinerary.getId();
        return new InviteLinkResponse(link);
    }
}