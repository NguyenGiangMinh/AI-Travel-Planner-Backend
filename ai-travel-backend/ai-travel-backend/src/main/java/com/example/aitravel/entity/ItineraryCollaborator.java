package com.example.aitravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itinerary_collaborators")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CollaboratorId.class)
public class ItineraryCollaborator {

    @Id
    @Column(name = "itinerary_id")
    private Long itineraryId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", insertable = false, updatable = false)
    @JsonIgnore
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.EDITOR;

    @Column(name = "invited_by")
    private Long invitedBy;

    public enum Role {
        EDITOR, VIEWER
    }
}