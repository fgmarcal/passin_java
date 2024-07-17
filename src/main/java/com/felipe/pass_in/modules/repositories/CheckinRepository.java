package com.felipe.pass_in.modules.repositories;

import com.felipe.pass_in.modules.entities.CheckinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CheckinRepository extends JpaRepository<CheckinEntity, Integer> {

    Optional<CheckinEntity> findByAttendeeId(String attendeeId);
}
