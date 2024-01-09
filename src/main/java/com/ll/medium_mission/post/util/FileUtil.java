package com.ll.medium_mission.post.util;

import com.ll.medium_mission.global.exception.FileNameNotValidException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

    public String getDatePath(String path, String date) {
        return path + date;
    }

    public String getUUIDFileName(String originalFilename, String date) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        return date.replaceAll("-", "_") + "_" + uuid + "." + extension;
    }

    public String getUploadDate(String filename) {
        Pattern pattern = Pattern.compile("\\d{4}_\\d{2}_\\d{2}");
        Matcher matcher = pattern.matcher(filename);

        if (!matcher.find()) {
            throw new FileNameNotValidException();
        }

        return matcher.group().replace('_', '-');
    }

}
