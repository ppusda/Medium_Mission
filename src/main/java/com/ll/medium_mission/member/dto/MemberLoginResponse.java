package com.ll.medium_mission.member.dto;

import lombok.Builder;

@Builder
public record MemberLoginResponse(String accessToken, String refreshToken){

}
