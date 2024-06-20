package com.sptp.dawnary.global.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
