package com.ll.medium_mission.member.dto;

import lombok.Builder;

@Builder
public record MemberCheckResponse(String email, boolean result) {

}
