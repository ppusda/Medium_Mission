package com.ll.medium_mission.global.exception;

public class FileNameNotValidException extends MediumException{

    private static final String MESSAGE = "이미지 형식이 잘못되었습니다.";

    public FileNameNotValidException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
