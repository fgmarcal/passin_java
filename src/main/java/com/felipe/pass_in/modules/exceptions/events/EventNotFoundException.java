package com.felipe.pass_in.modules.exceptions.events;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message){
        super(message);
    }
}
