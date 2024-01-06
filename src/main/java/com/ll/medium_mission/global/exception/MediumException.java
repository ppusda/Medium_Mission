package com.ll.medium_mission.global.exception;

public abstract class MediumException extends RuntimeException{

    public MediumException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
