package com.sptp.dawnary.image.controller;

import com.sptp.dawnary.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("imageFile") MultipartFile imageFile) {
        String fileName = imageService.saveImage(imageFile);
        return new ResponseEntity<>(fileName, OK);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getFile(@PathVariable("imageName") String imageName) throws UnsupportedEncodingException {
        byte[] imageBytes = imageService.getImageBytes(imageName);
        String encodedFilename = URLEncoder.encode(imageName, UTF_8.toString()).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFilename)
                .body(imageBytes);
    }

}
