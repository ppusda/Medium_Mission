package com.ll.medium_mission.global.exception;

public class ImageDownloadException extends MediumException{

    private static final String MESSAGE = "이미지를 다운로드 할 수 없습니다.";

    public ImageDownloadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
