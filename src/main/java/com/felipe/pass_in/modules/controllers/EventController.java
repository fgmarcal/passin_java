package com.felipe.pass_in.modules.controllers;

import com.felipe.pass_in.modules.dto.attendee.AttendeeIdDTO;
import com.felipe.pass_in.modules.dto.attendee.AttendeeListResponseDTO;
import com.felipe.pass_in.modules.dto.attendee.AttendeeRequestDTO;
import com.felipe.pass_in.modules.dto.event.EventIdDTO;
import com.felipe.pass_in.modules.dto.event.EventRequestDTO;
import com.felipe.pass_in.modules.dto.event.EventResponseDTO;
import com.felipe.pass_in.modules.entities.EventsEntity;
import com.felipe.pass_in.modules.services.AttendeeService;
import com.felipe.pass_in.modules.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable("id") String eventId){
            var details = this.eventService.getEventDetail(eventId);
            return ResponseEntity.ok().body(details);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventsEntity>> getAllEvents(){
        var details = this.eventService.getAllEvents();
        return ResponseEntity.ok().body(details);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerAttendee(@PathVariable String eventId, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, attendeeRequestDTO);

        var uri = uriComponentsBuilder.path("/attendees/{eventId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable("id") String eventId){
        var details = this.attendeeService.getEventsAttendees(eventId);
        return ResponseEntity.ok().body(details);
    }
}
