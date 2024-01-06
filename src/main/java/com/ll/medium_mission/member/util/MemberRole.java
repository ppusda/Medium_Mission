package com.ll.medium_mission.member.util;

import lombok.Getter;

@Getter
public enum MemberRole {
    USER("ROLE_USER"),
    PAID("ROLE_PAID");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
