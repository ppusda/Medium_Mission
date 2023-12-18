package com.ll.medium_mission.member.dto;

import lombok.Builder;

@Builder
public record MemberCheckResponse(String nickname, boolean result) {

}
