package com.ll.medium_mission.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostRequest(
        @Size(max = 100)
        @NotEmpty(message = "제목은 필수 항목 입니다.")
        String title,
        @NotEmpty(message = "내용은 필수 항목 입니다.")
        String content
){

}
