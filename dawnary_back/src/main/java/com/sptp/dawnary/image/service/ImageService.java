package com.sptp.dawnary.image.service;

import com.sptp.dawnary.global.exception.EmptyImageException;
import com.sptp.dawnary.global.exception.ImageNotFoundException;
import com.sptp.dawnary.global.exception.ImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageService {

    private static final String UPLOAD_DIR = "C:/nginx/html/images/";

    public String saveImage(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new EmptyImageException();
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String original = imageFile.getOriginalFilename();
            String extension = getExtension(original);
            String uuid = UUID.randomUUID().toString() + extension;
            Path path = uploadPath.resolve(uuid);
            Files.write(path, imageFile.getBytes());

            return uuid;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageException();
        }
    }

    public byte[] getImageBytes(String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);

            if (Files.exists(path)) {
                byte[] imageBytes = Files.readAllBytes(path);
                return imageBytes;
            }

            throw new ImageNotFoundException();

        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageException();
        }
    }

    private String getExtension(String fileName) {
        int idx = fileName.lastIndexOf('.');
        return (idx == -1) ? "" : fileName.substring(idx);
    }
}
