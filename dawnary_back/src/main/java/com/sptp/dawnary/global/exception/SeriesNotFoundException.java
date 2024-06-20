package com.sptp.dawnary.global.exception;

public class SeriesNotFoundException extends IllegalArgumentException{

    public SeriesNotFoundException(String message) {
        super(message);
    }
}
