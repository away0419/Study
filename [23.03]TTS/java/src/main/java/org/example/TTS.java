package org.example;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class TTS {

    public static void main(String... args) throws Exception {
        System.out.println(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));

        // 클라이언트 연결하기
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // 변환할 텍스트
            SynthesisInput input = SynthesisInput.newBuilder().setText("Hello, World!").build();

            // 목소리의 언어와 성별 등을 설정
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("en-US")
                            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                            .build();

            // 오디오 파일 설정
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build();

            // text, 설정으로 요청 보내기
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // 응답
            ByteString audioContents = response.getAudioContent();

            // 해당 응답을 오디오 파일로 만들기
            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            }
        }
    }
}