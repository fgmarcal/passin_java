package com.felipe.pass_in.modules.dto.event;

public record EventDetailDTO(String id,
                             String title,
                             String details,
                             String slug,
                             Integer maximumAttendees,
                             Integer attendeesCount
    ) {
}
