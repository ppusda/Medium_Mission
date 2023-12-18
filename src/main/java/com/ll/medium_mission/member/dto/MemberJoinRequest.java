package com.ll.medium_mission.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberJoinRequest (
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
        String email,
        @Size(message = "닉네임은 30자를 넘어갈 수 없습니다.")
        String nickname,
        @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
        String password,
        @NotBlank(message = "비밀번호 확인은 필수로 입력해야 합니다.")
        String passwordConfirm){

}
