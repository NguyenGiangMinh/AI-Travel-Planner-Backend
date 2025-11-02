package com.example.ai_travel_planner.controller;

import com.example.travelplanner.model.ItineraryCollaborator;
import com.example.ai_travel_planner.service.CollaboratorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {
    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping("/itinerary/{itineraryId}")
    public List<ItineraryCollaborator> getCollaborators(@PathVariable Long itineraryId) {
        return collaboratorService.getCollaboratorsByItinerary(itineraryId);
    }

    @PostMapping
    public ItineraryCollaborator addCollaborator(@RequestBody ItineraryCollaborator collaborator) {
        return collaboratorService.addCollaborator(collaborator);
    }
}
