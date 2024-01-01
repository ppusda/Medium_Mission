package com.ll.medium_mission.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MemberModifyRequest(
        @Size(message = "닉네임은 15자를 넘어갈 수 없습니다.")
        String nickname,
        @NotBlank(message = "현재 비밀번호는 필수로 입력해야 합니다.")
        String password,
        String newPassword,
        String newPasswordConfirm){

}
