package com.ll.medium_mission.member.dto;

import lombok.Builder;

@Builder
public record MemberResponse(String email, String nickname) { }
