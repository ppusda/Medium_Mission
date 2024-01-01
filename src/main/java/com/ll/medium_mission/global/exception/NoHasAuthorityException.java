package com.ll.medium_mission.global.exception;

public class NoHasAuthorityException extends MediumException{

    private static final String MESSAGE = "권한이 없습니다.";

    public NoHasAuthorityException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
