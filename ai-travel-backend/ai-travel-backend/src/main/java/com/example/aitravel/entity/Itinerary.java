    package com.example.aitravel.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    @Table(name = "itineraries")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Itinerary {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "owner_id", nullable = false)
        private User owner;

        private String title;
        private Integer days;
        private BigDecimal budget;
        private String style;

        @Column(columnDefinition = "json")
        private String payload;

        @Enumerated(EnumType.STRING)
        @Column(length = 10)
        @Builder.Default
        private Visibility visibility = Visibility.private_;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<ItineraryCollaborator> collaborators;

        public enum Visibility {
            private_,
            shared,
            public_
        }
    }