package com.ll.medium_mission.post.dto;

import lombok.Builder;

@Builder
public record RecommendCheckResponse (
        Long recommendCount,
        boolean isRecommended
){
}
