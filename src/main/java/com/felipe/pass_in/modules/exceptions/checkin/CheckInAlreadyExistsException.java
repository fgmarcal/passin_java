package com.felipe.pass_in.modules.exceptions.checkin;

public class CheckInAlreadyExistsException extends RuntimeException{
    public CheckInAlreadyExistsException(String message){
        super(message);
    }
}
