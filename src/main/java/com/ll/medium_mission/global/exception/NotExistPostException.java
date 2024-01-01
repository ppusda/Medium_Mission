package com.ll.medium_mission.global.exception;

public class NotExistPostException extends MediumException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public NotExistPostException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
