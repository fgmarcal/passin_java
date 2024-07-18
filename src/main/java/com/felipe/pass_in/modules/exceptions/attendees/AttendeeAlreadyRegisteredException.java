package com.felipe.pass_in.modules.exceptions.attendees;

public class AttendeeAlreadyRegisteredException extends RuntimeException{
    public AttendeeAlreadyRegisteredException(String message){
        super(message);
    }
}
