package com.example.ai_travel_planner.service;

import com.example.travelplanner.model.ItineraryCollaborator;
import com.example.ai_travel_planner.repository.ItineraryCollaboratorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollaboratorService {
    private final ItineraryCollaboratorRepository collaboratorRepository;

    public CollaboratorService(ItineraryCollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    public List<ItineraryCollaborator> getCollaboratorsByItinerary(Long itineraryId) {
        return collaboratorRepository.findByItineraryId(itineraryId);
    }

    public ItineraryCollaborator addCollaborator(ItineraryCollaborator collaborator) {
        return collaboratorRepository.save(collaborator);
    }
}
