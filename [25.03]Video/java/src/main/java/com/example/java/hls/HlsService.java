package com.example.java.hls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HlsService {
    private static final String FFMPEG_PATH = "ffmpeg"; // FFmpeg 실행 파일 경로 (시스템 PATH에 있다면 그냥 "ffmpeg")

    // 📌 HLS 기본 변환
    public void basicConvert(String inputFilePath, String outputDir) {

        // FFMPEG 명령어
        String outputFilePath = outputDir + "/output.m3u8"; // ffmpeg 변환 후 리스트 파일 경로
        String[] command = {
            FFMPEG_PATH,
            "-i", inputFilePath,    // 입력파일 지정
            "-c:v", "copy",         // 비디오 코덱 복사
            "-c:a", "copy",         // 오디오 코덱 복사
            // "-codec:copy",          // 인코딩 없이 원본 코덱 그대로 복사 -> 오류 발생
            "-start_number", "0",   // 세그먼트 번호를 0부터 시작
            "-hls_time", "10",      // 각 hls 세그먼트 길이를 10초로 설정
            "-hls_list_size", "0",  // 전체 리스트 유지 (0이면 모든 세그먼트 저장)
            "-f", "hls", outputFilePath // HLS 플레이리스트 파일 지정
        };

        convertToHls(inputFilePath, outputDir, command);
    }

    // 📌 HLS 여러 화질 변환 실제 유튜브, 넷플릭스에서 사용하는 화질 (화질 별로 hls 생성 및 리스트 생성, 이후 마스터 리스트 생성)
    public void multipleConvert(String inputFilePath, String outputDir) throws IOException {

        String[] command = {
            FFMPEG_PATH,
            "-i", inputFilePath,  // 입력 파일
            "-filter_complex", "[0:v]split=4[v1][v2][v3][v4];" +
            "[v1]scale=w=640:h=360[v1out];" +
            "[v2]scale=w=854:h=480[v2out];" +
            "[v3]scale=w=1280:h=720[v3out];" +
            "[v4]scale=w=1920:h=1080[v4out]",

            // 360p 변환
            "-map", "[v1out]", "-c:v", "libx264", "-b:v", "800k",
            "-hls_time", "6", "-hls_playlist_type", "vod",
            "-f", "hls", outputDir + "/360p.m3u8",

            // 480p 변환
            "-map", "[v2out]", "-c:v", "libx264", "-b:v", "1400k",
            "-hls_time", "6", "-hls_playlist_type", "vod",
            "-f", "hls", outputDir + "/480p.m3u8",

            // 720p 변환
            "-map", "[v3out]", "-c:v", "libx264", "-b:v", "2800k",
            "-hls_time", "6", "-hls_playlist_type", "vod",
            "-f", "hls", outputDir + "/720p.m3u8",

            // 1080p 변환
            "-map", "[v4out]", "-c:v", "libx264", "-b:v", "5000k",
            "-hls_time", "6", "-hls_playlist_type", "vod",
            "-f", "hls", outputDir + "/1080p.m3u8"
        };

        convertToHls(inputFilePath, outputDir, command);
        createMasterPlaylist(outputDir);
    }

    // 📌 multiple 시 master.m3u8 수동으로 만들기 (명령어 사용 시 자동으로 안만들어져서 추가함.)
    private static void createMasterPlaylist(String outputDir) throws IOException {
        File masterPlaylist = new File(outputDir + "/master.m3u8");
        if (!masterPlaylist.exists()) {
            masterPlaylist.createNewFile();
        }

        String masterContent = "#EXTM3U\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=800000,RESOLUTION=640x360\n360p.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=1400000,RESOLUTION=854x480\n480p.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=2800000,RESOLUTION=1280x720\n720p.m3u8\n"
            + "#EXT-X-STREAM-INF:BANDWIDTH=5000000,RESOLUTION=1920x1080\n1080p.m3u8\n";

        try (FileWriter writer = new FileWriter(masterPlaylist)) {
            writer.write(masterContent);
        }
        System.out.println("✅ Master Playlist 생성 완료!");
    }

    // 📌 HLS TS 파일 변환
    private void convertToHls(String inputFilePath, String outputDir, String[] command) {

        // 입력 파일 확인
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            log.error("입력 파일이 존재하지 않습니다: " + inputFilePath);
            return;
        }

        // 디렉토리 확인
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs(); // 출력 디렉토리가 없으면 생성
        }

        // 디렉토리 쓰기 권한 확인
        File testFile = new File(outputDir, "test.txt");
        try {
            if (testFile.createNewFile()) {
                testFile.delete();
            } else {
                log.error("출력 디렉토리에 파일을 생성할 수 없습니다.");
                return;
            }
        } catch (IOException e) {
            log.error("출력 디렉토리 쓰기 테스트 실패: " + e.getMessage());
            return;
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true); // 표준 에러를 표준 출력으로 병합
        builder.directory(new File(outputDir)); // 실행 디렉토리 설정

        try {
            Process process = builder.start();

            // FFmpeg 로그 출력
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info(line);  // FFmpeg 실행 로그 출력
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.error("FFmpeg 실행 실패, 코드: " + exitCode);
            } else {
                log.info("HLS 변환 완료: {}");
            }

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("FFmpeg 실행 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
