package com.felipe.pass_in.modules.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventsEntity event;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
