package com.felipe.pass_in.modules.services;

import com.felipe.pass_in.modules.dto.attendee.AttendeeIdDTO;
import com.felipe.pass_in.modules.dto.attendee.AttendeeRequestDTO;
import com.felipe.pass_in.modules.dto.event.EventIdDTO;
import com.felipe.pass_in.modules.dto.event.EventRequestDTO;
import com.felipe.pass_in.modules.dto.event.EventResponseDTO;
import com.felipe.pass_in.modules.entities.AttendeesEntity;
import com.felipe.pass_in.modules.entities.EventsEntity;
import com.felipe.pass_in.modules.exceptions.events.EventIsFullException;
import com.felipe.pass_in.modules.exceptions.events.EventNotFoundException;
import com.felipe.pass_in.modules.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId){
        var event = getEventById(eventId);
        List<AttendeesEntity> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public List<EventsEntity> getAllEvents(){
        var events = this.eventRepository.findAll();
        return events;
    }

    public EventIdDTO createEvent(EventRequestDTO event){
        EventsEntity newEvent = new EventsEntity();

        newEvent.setTitle(event.title());
        newEvent.setDetails(event.details());
        newEvent.setMaximumAttendees(event.maximumAttendees());
        newEvent.setSlug(this.createSlug(event.title()));

        this.eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+","-")
                .toLowerCase();
    }

    private EventsEntity getEventById(String eventId){
        return this.eventRepository.findById(eventId)
                .orElseThrow(()-> new EventNotFoundException(("Event not found with ID: " + eventId)));
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(),eventId);

        var event = getEventById(eventId);
        List<AttendeesEntity> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendeeList.size()){
            throw new EventIsFullException("Event is full");
        }
        AttendeesEntity newAttendee = new AttendeesEntity();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);
        return new AttendeeIdDTO(newAttendee.getId());
    }

}
