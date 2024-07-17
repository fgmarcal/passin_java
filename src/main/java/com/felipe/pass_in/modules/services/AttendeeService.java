package com.felipe.pass_in.modules.services;

import com.felipe.pass_in.modules.dto.attendee.AttendeeDetails;
import com.felipe.pass_in.modules.dto.attendee.AttendeeListResponseDTO;
import com.felipe.pass_in.modules.entities.AttendeesEntity;
import com.felipe.pass_in.modules.entities.CheckinEntity;
import com.felipe.pass_in.modules.repositories.AttendeeRepository;
import com.felipe.pass_in.modules.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private CheckinRepository checkInRepository;

    public List<AttendeesEntity> getAllAttendeesFromEvent(String eventId){
        var attendeeList = this.attendeeRepository.findByEventId(eventId);
        return attendeeList;
    }

    public AttendeeListResponseDTO getEventsAttendees(String eventId){
        List<AttendeesEntity> attendeesList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeesList.stream().map(attendee ->{
            Optional<CheckinEntity> checkin = this.checkInRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkInAt = checkin.<LocalDateTime>map(CheckinEntity::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }
}
