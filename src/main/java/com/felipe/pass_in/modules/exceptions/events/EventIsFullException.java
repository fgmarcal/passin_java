package com.felipe.pass_in.modules.exceptions.events;

public class EventIsFullException extends RuntimeException{
    public EventIsFullException(String message){
        super(message);
    }
}
