package com.felipe.pass_in.modules.dto.event;

import com.felipe.pass_in.modules.entities.EventsEntity;
import lombok.Data;
import lombok.Getter;

@Getter
public class EventResponseDTO {

    private EventDetailDTO eventDetailDTO;

    public EventResponseDTO(EventsEntity event, Integer numberOfAttendees){
        this.eventDetailDTO = new EventDetailDTO(event.getId(),
                event.getTitle(),
                event.getDetails(),
                event.getSlug(),
                event.getMaximumAttendees(),
                numberOfAttendees);
    }

}
