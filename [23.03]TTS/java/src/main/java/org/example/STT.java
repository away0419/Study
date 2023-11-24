package org.example;// Imports the Google Cloud client library

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class STT {

    public static void main(String... args) throws Exception {
        // 구글 STT 클라이언트 생성
        try (SpeechClient speechClient = SpeechClient.create()) {

            // 음성 파일이 있는 경로
            String filePath = "D:/project/TTS/java/output.mp3";

            // 오디오 파일에 대한 설정부분
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(24000)
                    .setLanguageCode("en-US")
                    .build();

//          1분 이하의 오디오인 경우
            RecognitionAudio audio = getRecognitionAudio(filePath); // Audio 파일에 대한 RecognitionAudio 인스턴스 생성
            RecognizeResponse response = speechClient.recognize(config, audio); // 요청에 대한 응답


//            Non-Blocking 으로 호출을 하며 1분 이상의 긴 파일 일때는 LongRunningRecognizeResponse를 사용함
//            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response =
//                    speechClient.longRunningRecognizeAsync(config, audio);
//
//            while (!response.isDone()) {
//                System.out.println("Waiting for response...");
//                Thread.sleep(10000);
//            }

            List<SpeechRecognitionResult> results = response.getResultsList(); // 응답 결과들

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }
        }
    }

    // Local 이나 Remote이거나 구분해서 RecognitionAudio 만들어 주는 부분
    public static RecognitionAudio getRecognitionAudio(String filePath) throws IOException {
        RecognitionAudio recognitionAudio;

        // 파일이 GCS에 있는 경우
        if (filePath.startsWith("gs://")) {
            recognitionAudio = RecognitionAudio.newBuilder()
                    .setUri(filePath)
                    .build();
        } else { // 파일이 로컬에 있는 경우
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            recognitionAudio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();
        }

        return recognitionAudio;
    }
}