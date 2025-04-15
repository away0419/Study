> ## 정적 리소스로 제공
- resources/static 폴더에 저장한 뒤 해당 파일을 불러오는 방식.
  - ex) resource/static/video/test.mp4 -> http://localhost:8080/video/test.mp4
  - 가장 간단한 방법
  - 동영상 탐색 불가능 (첫 시작점 선택 불가)
  - 한번에 불러와 보여주기 때문에 대용량 또는 트래픽 많을 시 성능 저하
  - 기본적으로 해당 프로젝트에 파일을 넣어야 함

<details>
  <summary>StaticResourceConfig</summary>

- 파일 저장 폴더 변경 또는 URL 변경 하려면 설정 추가.

  ```java
  package com.example.java.resource;
  
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  
  @Configuration
  public class StaticResourceConfig implements WebMvcConfigurer {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
          // 외부 비디오 파일 경로 (Windows 경로)
          registry.addResourceHandler("/media/**")
              .addResourceLocations("file:///C:/videos/") // 윈도우: 반드시 세 개의 슬래시!
              .setCachePeriod(3600); // 캐시 (초 단위)
  
          // 정적 자원 (내부 classpath)
          registry.addResourceHandler("/assets/**")
              .addResourceLocations("classpath:/static/assets/")
              .setCachePeriod(3600);
  
          // 하나의 URL 여러 경로
          registry.addResourceHandler("/static/**")
              .addResourceLocations(
                  "classpath:/static/common/",     // 1순위
                  "file:///C:/custom-resources/",  // 2순위
                  "classpath:/static/default/"     // 3순위
              );
      }
  }
  
  ```

</details>
<br/>
<br/>

> ## HttpRange
- 클라이언트가 헤더에 Range 정보를 입력하여 사용하여 특정 부분의 데이터를 요청하면 해당 범위의 데이터를 반환하는 방법.
  - 클라이언트가 Range 정보를 입력하지 않을 경우 브라우저에서 자동으로 입력해주는 경우도 있음.
  - Range 정보가 없는 경우 서버에서 파일 전체를 보내줌.
- 일반적인 MP4, MKV 동영상 파일 제공할 때 사용.

<details>
  <summary>VideoController</summary>

- 비디오 리스소 가져오는 방법은 다양함.

  ```java
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
  
  ```

</details>
  
<br/>
<br/>

> ## HLS 스트리밍

- Apple이 개발한 HTTP 기반 스트리밍 프토토콜.
- 기존 영상 파일을 .ts로 분할하고 .m3u8(재생목록)을 통해 관리.
- 적응형 비트레이트 스트리밍(ABR) 지원하며 네트워크 환경에 따라 동적으로 품질 조정 가능.

<details>
  <summary>gradle</summary>

- 기존 영상 파일을 분할하기 위해선 로컬에 FFmpeg가 설치 되어 있어야함. (구글 검색) 
- 이후 FFmpeg 사용을 도와주는 라이브러리 설치.

  ```gradle
  implementation 'org.bytedeco:javacv:1.5.8' // FFmpeg 사용을 위해 추가
  ```

</details>

<details>
  <summary>HlsService</summary>

- 기존 영상을 분할해주기 위한 서비스.
- FFmpeg 명령어를 입력 후 프로세스 실행하여 분할하는 방식.
- 여러 설정을 통해 화질 별로 생성 가능.
- master.m3u8은 앞서 만든 여러 화질중에서 네트워크 상태에 따라 자동으로 화질을 선택해주는 리스트. 
- front 설정은 react 방식으로 만들었으니 참조.

  ```java
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
  
  ```

</details>

<details>
  <summary>HlsController</summary>

- 사용자 요청에 따라 기본 영상 또는 멀티 화질 영상 응답.
- m3u8과 ts 요청의 URI는 동일해야 함. 또한, 파일명의 prefix 동일해야 자동으로 ts 파일 요청이 들어옴. 
  - 만약 ts 이름을 바꾸고 싶다면 front 요청에서 변경해야함.

  ```java
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
  
  ```

</details>

