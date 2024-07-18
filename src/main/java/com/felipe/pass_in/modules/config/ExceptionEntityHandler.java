package com.felipe.pass_in.modules.config;

import com.felipe.pass_in.modules.dto.general.ErrorResponseDTO;
import com.felipe.pass_in.modules.exceptions.attendees.AttendeeAlreadyRegisteredException;
import com.felipe.pass_in.modules.exceptions.attendees.AttendeeNotFoundException;
import com.felipe.pass_in.modules.exceptions.checkin.CheckInAlreadyExistsException;
import com.felipe.pass_in.modules.exceptions.events.EventIsFullException;
import com.felipe.pass_in.modules.exceptions.events.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(EventIsFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventIsFull(EventIsFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeAlreadyRegistered(AttendeeAlreadyRegisteredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExists(CheckInAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


}
