package com.example.aitravel.controller;

import com.example.aitravel.entity.User;
import com.example.aitravel.entity.ItineraryCollaborator;
import com.example.aitravel.service.CollaboratorService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/itineraries/{itineraryId}/collaborators")
public class CollaboratorController {
    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping
    public List<ItineraryCollaborator> getCollaborators(@PathVariable Long itineraryId) {
        return collaboratorService.getCollaboratorsByItinerary(itineraryId);
    }

    @PostMapping("/invite")
    public ItineraryCollaborator inviteCollaborator(
            @PathVariable Long itineraryId,
            @RequestParam String email,
            @RequestParam ItineraryCollaborator.Role role,
            Authentication authentication) {

        User invitingUser = (User) authentication.getPrincipal();

        return collaboratorService.inviteByEmail(itineraryId, email, role, invitingUser);
    }
}