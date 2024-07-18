package com.felipe.pass_in.modules.controllers;

import com.felipe.pass_in.modules.dto.attendee.AttendeeBadgeResponseDTO;
import com.felipe.pass_in.modules.services.AttendeeService;
import com.felipe.pass_in.modules.services.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;


    @GetMapping("/{id}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable("id") String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        var response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        this.attendeeService.checkInAttendee(attendeeId);

        return ResponseEntity.created(uri).build();
    }
}
