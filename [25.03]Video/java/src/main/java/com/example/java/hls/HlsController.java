package com.example.java.hls;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hls")
@RequiredArgsConstructor
@Slf4j
public class HlsController {

    // @Value("${video.guide.path}")
    private final String HLS_PATH = "D:/github/Study_2023-2024/[25.03]Video/java/src/main/resources/static/video";
    private final HlsService hlsService;

    // 📌 HLS TS 기본 변환
    @GetMapping("/convert/basic/{videoName}.mp4")
    public String basicConvertVideo(@PathVariable String videoName) {
        hlsService.basicConvert(HLS_PATH + "/" + videoName + ".mp4", HLS_PATH + "/" + videoName);
        return "HLS 변환 완료";
    }

    // 📌 HLS TS 멀티 변환
    @GetMapping("/convert/multiple/{videoName}.mp4")
    public String multipleConvertVideo(@PathVariable String videoName) throws IOException {
        hlsService.multipleConvert(HLS_PATH + "/" + videoName + ".mp4", HLS_PATH + "/multiple/" + videoName);
        return "HLS 변환 완료";
    }

    // 📌 HLS 기본 재생 목록(.m3u8) 반환 URI
    @GetMapping("/basic/{videoName}/{fileName}.m3u8")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Resource> getBasicHlsPlaylist(
        @PathVariable String videoName, @PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(HLS_PATH,  videoName, fileName+".m3u8");

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl")); // HLS MIME 타입

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }

    // 📌 HLS TS 기본 파일 반환 (URI는 위에 작성한 .m3u8의 URI와 동일하게 작성)
    @GetMapping("/basic/{videoName}/{fileName}.ts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Resource> getBasicHlsSegment(
        @PathVariable String videoName, @PathVariable String fileName) throws
        IOException {
        Path filePath = Paths.get(HLS_PATH,  videoName, fileName + ".ts");
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }

    // 📌 HLS 멀티 재생 목록(.m3u8) 반환 URI
    @GetMapping("/multiple/{videoName}/{fileName}.m3u8")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Resource> getMultipleHlsPlaylist(
        @PathVariable String videoName, @PathVariable String fileName) throws IOException {
        Path filePath = Paths.get(HLS_PATH, "multiple", videoName, fileName+".m3u8");

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.apple.mpegurl")); // HLS MIME 타입

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }

    // 📌 HLS TS 멀티 파일 반환 (URI는 위에 작성한 .m3u8의 URI와 동일하게 작성)
    @GetMapping("/multiple/{videoName}/{fileName}.ts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Resource> getMultipleHlsSegment(
        @PathVariable String videoName, @PathVariable String fileName) throws
        IOException {
        Path filePath = Paths.get(HLS_PATH, "multiple", videoName, fileName + ".ts");
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
            .headers(headers)
            .body(resource);
    }
}
