package com.example.aitravel.controller;

import com.example.aitravel.entity.Itinerary;
import com.example.aitravel.entity.User;
import com.example.aitravel.service.ItineraryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.aitravel.entity.InviteLinkResponse;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping("/my-itineraries")
    public List<Itinerary> getUserItineraries(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return itineraryService.getUserItineraries(currentUser.getId());
    }

    @PostMapping
    public Itinerary createItinerary(@RequestBody Itinerary itinerary) {
        return itineraryService.createItinerary(itinerary);
    }

    @GetMapping("/{id}")
    public Itinerary getItinerary(@PathVariable Long id) {
        return itineraryService.getItineraryById(id);
    }

    @GetMapping("/{itineraryId}/invite-link")
    public InviteLinkResponse getInviteLink(
            @PathVariable Long itineraryId,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        return itineraryService.generateInviteLink(itineraryId, currentUser);
    }
}