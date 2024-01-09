package com.ll.medium_mission.post.controller;

import com.ll.medium_mission.global.config.AppConfig;
import com.ll.medium_mission.global.exception.ImageDownloadException;
import com.ll.medium_mission.global.exception.ImageNotFoundException;
import com.ll.medium_mission.global.exception.ImageUploadException;
import com.ll.medium_mission.post.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/tui")
public class ToastEditorController {
    private String path;
    private final FileUtil fileUtil;

    public ToastEditorController(AppConfig appConfig, FileUtil fileUtil) {
        this.path = appConfig.getPath();
        this.fileUtil = fileUtil;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image")
    public String uploadEditorImage(@RequestParam final MultipartFile image) {
        if (image.isEmpty()) {
            return "";
        }

        String date = String.valueOf(LocalDate.now());
        String originalFilename = image.getOriginalFilename();

        String saveFilename = fileUtil.getUUIDFileName(originalFilename, date);
        String datePath = fileUtil.getDatePath(path, date);

        String fileFullPath = Paths.get(datePath, saveFilename).toString();

        File dir = new File(datePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return saveFilename;

        } catch (IOException e) {
            throw new ImageUploadException();
        }
    }

    @GetMapping(value = "/image", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam final String filename) {
        String uploadDate = fileUtil.getUploadDate(filename);
        String datePath = fileUtil.getDatePath(path, uploadDate);

        String fileFullPath = Paths.get(datePath, filename).toString();

        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new ImageNotFoundException();
        }

        try {
            return Files.readAllBytes(uploadedFile.toPath());
        } catch (IOException e) {
            throw new ImageDownloadException();
        }
    }
}
