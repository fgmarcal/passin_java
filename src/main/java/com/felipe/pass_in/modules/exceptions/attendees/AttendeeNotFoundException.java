package com.felipe.pass_in.modules.exceptions.attendees;

public class AttendeeNotFoundException extends RuntimeException{
    public AttendeeNotFoundException(String message){
        super(message);
    }
}
