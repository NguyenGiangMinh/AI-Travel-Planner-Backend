package com.example.aitravel.service;

import com.example.aitravel.entity.ItineraryCollaborator;
import com.example.aitravel.repository.ItineraryCollaboratorRepository;
import com.example.aitravel.repository.ItineraryRepository;
import com.example.aitravel.repository.UserRepository;
import com.example.aitravel.entity.Itinerary;
import com.example.aitravel.entity.User;
import com.example.aitravel.entity.CollaboratorId;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CollaboratorService {
    private final ItineraryCollaboratorRepository collaboratorRepository;
    private final UserRepository userRepository;
    private final ItineraryRepository itineraryRepository;

    public CollaboratorService(ItineraryCollaboratorRepository collaboratorRepository,
                               UserRepository userRepository,
                               ItineraryRepository itineraryRepository) {
        this.collaboratorRepository = collaboratorRepository;
        this.userRepository = userRepository;
        this.itineraryRepository = itineraryRepository;
    }

    public List<ItineraryCollaborator> getCollaboratorsByItinerary(Long itineraryId) {
        return collaboratorRepository.findByItineraryIdWithUser(itineraryId);
    }

    public ItineraryCollaborator inviteByEmail(Long itineraryId, String email, ItineraryCollaborator.Role role, User invitingUser) {

        User userToInvite = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found: " + email));

        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));

        if (!itinerary.getOwner().getId().equals(invitingUser.getId())) {
            throw new RuntimeException("Only the owner can invite collaborators");
        }

        CollaboratorId newCollabId = new CollaboratorId(itineraryId, userToInvite.getId());
        if (collaboratorRepository.existsById(newCollabId)) {
            throw new RuntimeException("User is already a collaborator");
        }

        ItineraryCollaborator collaborator = new ItineraryCollaborator();
        collaborator.setItineraryId(itineraryId);
        collaborator.setUserId(userToInvite.getId());
        collaborator.setRole(role);
        collaborator.setInvitedBy(invitingUser.getId());

        return collaboratorRepository.save(collaborator);
    }
}