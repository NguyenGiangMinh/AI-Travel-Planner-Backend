package com.example.aitravel.entity;

import java.io.Serializable;
import java.util.Objects;

public class CollaboratorId implements Serializable {
    private Long itineraryId;
    private Long userId;

    // Constructors
    public CollaboratorId() {
    }

    public CollaboratorId(Long itineraryId, Long userId) {
        this.itineraryId = itineraryId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollaboratorId that = (CollaboratorId) o;
        return Objects.equals(itineraryId, that.itineraryId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itineraryId, userId);
    }
}