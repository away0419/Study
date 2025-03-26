package com.example.java.range;

import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    private static final long CHUNK_SIZE = 1024 * 1024; // 1MB

    @GetMapping("/range")
    public ResponseEntity<ResourceRegion> range(@RequestHeader HttpHeaders headers) throws IOException {
        /*  다양한 비디오 리스소 가져오는 방법
            ClassPathResource videoResource = new ClassPathResource("static/" + filename); // resource 폴더에서 가져오는 방법
            ---
            File file = new File("D:/github/Study_2023-2024/[25.03]Video/java/src/main/resources/static/video/test.mp4" ); // 절대 경로에서 파일 가져오기
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r"); // 해당 파일 읽기만 가능한 파일로 만듬
            long contentLength = randomAccessFile.length();
            randomAccessFile.close();
            Resource video = new FileSystemResource(file);
         */
        FileSystemResource video = new FileSystemResource(
            "D:/github/Study_2023-2024/[25.03]Video/java/src/main/resources/static/video/test.mp4");
        long contentLength = video.contentLength();

        HttpRange range = headers.getRange()
            .stream()
            .findFirst()
            .orElse(HttpRange.createByteRange(0,
                contentLength - 1)); // 헤더에 포함된 range 가져오기 (range는 가져오려는 파일의 범위를 뜻함. 설정하지 않아도 대부분 자동으로 만들어서 요청이 들어옴)
        long rangeStart = range.getRangeStart(
            contentLength); // 영상의 시작점을 뜻함. (contentLength 매개변수로 받는 이유는 range 정보가 단순히 [시작 위치 ~ 끝 위치] 가 아니기 때문임. 시작 위치는 상대 적으로 달라 질 수 있음. ex) 1000 또는 -500 -> 1000에서 시작, -500이란 끝에서 500 만큼 앞 부분이란 뜻. 즉 총 영상 범위가 10000 일 경우 99500 이 시작점이라는 뜻.
        long rangeEnd = Math.min(range.getRangeEnd(contentLength),
            contentLength - 1); // 위와 동일. 여기서 range에 포함된 범위가 실제 영상 크기를 넘어갈 수 있기 때문에 min 함수 사용하는 것.
        long rangeLength = rangeEnd - rangeStart + 1; // 영상에서 가져오려는 파일 크기

        ResourceRegion resourceRegion = new ResourceRegion(video, rangeStart,
            rangeLength); // 시작 지점에서 파일 크기만큼만 가져와 객체로 만듬.

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(resourceRegion);
    }

}
