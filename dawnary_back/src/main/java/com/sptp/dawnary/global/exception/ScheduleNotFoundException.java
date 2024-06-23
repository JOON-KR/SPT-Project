package com.sptp.dawnary.global.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{

    private final static String message = "스케줄이 존재하지 않습니다.";

    public ScheduleNotFoundException() {
        super(message);
    }
}
