package com.ll.medium_mission.post.util;

import org.springframework.stereotype.Component;

@Component
public class PathUtil {

    public String getDatePath(String path, String date) {
        return path + date;
    }

}
