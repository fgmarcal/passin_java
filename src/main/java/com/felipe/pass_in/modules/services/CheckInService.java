package com.felipe.pass_in.modules.services;


import com.felipe.pass_in.modules.entities.AttendeesEntity;
import com.felipe.pass_in.modules.entities.CheckinEntity;
import com.felipe.pass_in.modules.exceptions.checkin.CheckInAlreadyExistsException;
import com.felipe.pass_in.modules.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CheckInService {

    @Autowired
    private CheckinRepository checkinRepository;

    public void registerCheckIn(AttendeesEntity attendee){
        this.verifyCheckInExists(attendee.getId());

        CheckinEntity newCheckIn = new CheckinEntity();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkinRepository.save(newCheckIn);
    }

    public Optional<CheckinEntity> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }

    private void verifyCheckInExists(String attendeeId){
        var isCheckedIn = this.getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()){
            throw new CheckInAlreadyExistsException("Attendee already checked in");
        }
    }

}
