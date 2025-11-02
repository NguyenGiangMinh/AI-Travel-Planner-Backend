package com.example.aitravel.repository;

import com.example.aitravel.entity.CollaboratorId;
import com.example.aitravel.entity.ItineraryCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryCollaboratorRepository extends JpaRepository<ItineraryCollaborator, CollaboratorId> {

    List<ItineraryCollaborator> findByItineraryId(Long itineraryId);

    List<ItineraryCollaborator> findByUserId(Long userId);

    @Query("SELECT ic FROM ItineraryCollaborator ic JOIN FETCH ic.user WHERE ic.itineraryId = :itineraryId")
    List<ItineraryCollaborator> findByItineraryIdWithUser(@Param("itineraryId") Long itineraryId);

    boolean existsByItineraryIdAndUserId(Long itineraryId, Long userId);
}