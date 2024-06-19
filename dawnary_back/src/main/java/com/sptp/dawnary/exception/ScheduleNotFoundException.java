package com.sptp.dawnary.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
