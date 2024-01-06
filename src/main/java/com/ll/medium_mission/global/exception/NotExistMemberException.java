package com.ll.medium_mission.global.exception;

public class NotExistMemberException extends MediumException{
    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NotExistMemberException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
