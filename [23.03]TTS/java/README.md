[TTS 공식 문서](https://cloud.google.com/text-to-speech/docs/create-audio-text-client-libraries?hl=ko#client-libraries-install-java)

[STT 공식 문서](https://cloud.google.com/speech-to-text/docs/transcribe-client-libraries?hl=ko#client-libraries-install-java)

<br/>
<br/>

> ## STT

- 1분 이상의 오디오는 Google Cloud Storage에 파일을 올려 이용해야함.
- [참고 블로그](https://since.tistory.com/29)

<br>
<br>

> ## 라이브러리

- Gradle

  ```
  implementation platform('com.google.cloud:libraries-bom:26.1.4')
  implementation 'com.google.cloud:google-cloud-texttospeech'

  implementation platform('com.google.cloud:libraries-bom:26.1.4')
  implementation 'com.google.cloud:google-cloud-speech'
  ```
