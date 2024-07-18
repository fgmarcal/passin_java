package com.felipe.pass_in.modules.services;

import com.felipe.pass_in.modules.dto.attendee.AttendeeBadgeResponseDTO;
import com.felipe.pass_in.modules.dto.attendee.AttendeeDetails;
import com.felipe.pass_in.modules.dto.attendee.AttendeeListResponseDTO;
import com.felipe.pass_in.modules.dto.attendee.AttendeeBadgeDTO;
import com.felipe.pass_in.modules.entities.AttendeesEntity;
import com.felipe.pass_in.modules.entities.CheckinEntity;
import com.felipe.pass_in.modules.exceptions.attendees.AttendeeAlreadyRegisteredException;
import com.felipe.pass_in.modules.exceptions.attendees.AttendeeNotFoundException;
import com.felipe.pass_in.modules.repositories.AttendeeRepository;
import com.felipe.pass_in.modules.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private CheckInService checkInService;

    private AttendeesEntity getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(
                ()-> new AttendeeNotFoundException("Attendee not found with provided Id: " + attendeeId));
    }

    public List<AttendeesEntity> getAllAttendeesFromEvent(String eventId){
        var attendeeList = this.attendeeRepository.findByEventId(eventId);
        return attendeeList;
    }

    public AttendeeListResponseDTO getEventsAttendees(String eventId){
        List<AttendeesEntity> attendeesList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeesList.stream().map(attendee ->{

            Optional<CheckinEntity> checkIn = this.checkInService.getCheckIn(attendee.getId());

            LocalDateTime checkInAt = checkIn.<LocalDateTime>map(CheckinEntity::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId){
       Optional<AttendeesEntity> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
       if(isAttendeeRegistered.isPresent()){
           throw new AttendeeAlreadyRegisteredException("Attendee already registered");
       }
    }

    public AttendeesEntity registerAttendee(AttendeesEntity attendee){
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeesEntity attendee = getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO badge = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(),uri, attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(badge);
    }

    public void checkInAttendee(String attendeeId){
        AttendeesEntity attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

}
