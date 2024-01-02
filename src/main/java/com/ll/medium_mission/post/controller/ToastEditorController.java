package com.ll.medium_mission.post.controller;

import com.ll.medium_mission.global.config.AppConfig;
import com.ll.medium_mission.global.exception.ImageDownloadException;
import com.ll.medium_mission.global.exception.ImageNotFoundException;
import com.ll.medium_mission.global.exception.ImageUploadException;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    private final MemberService memberService;

    public ToastEditorController(AppConfig appConfig, MemberService memberService) {
        this.path = appConfig.getPath();
        this.memberService = memberService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image")
    public String uploadEditorImage(@RequestParam final MultipartFile image, Principal principal) {
        if (image.isEmpty()) {
            return "";
        }

        String orgFilename = image.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = orgFilename.substring(orgFilename.lastIndexOf(".") + 1);
        String saveFilename = uuid + "." + extension;
        String fileFullPath = Paths.get(path, saveFilename).toString();
        log.info(fileFullPath);

        File dir = new File(path);
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/image", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] printEditorImage(@RequestParam final String filename) {
        String fileFullPath = Paths.get(path, filename).toString();

        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fileFullPath);
        if (!uploadedFile.exists()) {
            throw new ImageNotFoundException();
        }

        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            return Files.readAllBytes(uploadedFile.toPath());

        } catch (IOException e) {
            throw new ImageDownloadException();
        }
    }
}
