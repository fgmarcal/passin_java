package com.felipe.pass_in.modules.repositories;

import com.felipe.pass_in.modules.entities.AttendeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<AttendeesEntity, String> {
    List<AttendeesEntity> findByEventId(String eventId);

}
