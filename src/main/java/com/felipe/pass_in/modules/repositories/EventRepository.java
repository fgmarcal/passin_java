package com.felipe.pass_in.modules.repositories;

import com.felipe.pass_in.modules.entities.EventsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventsEntity, String> {
}
