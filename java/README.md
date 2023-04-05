[TTS 공식 문서](https://cloud.google.com/text-to-speech/docs/create-audio-text-client-libraries?hl=ko#client-libraries-install-java)

[STT 공식 문서](https://cloud.google.com/speech-to-text/docs/transcribe-client-libraries?hl=ko#client-libraries-install-java)

작성일 : 23.04.05

## TTS 의존성 추가

 - Gradle
    ```
    implementation platform('com.google.cloud:libraries-bom:26.1.4')
    implementation 'com.google.cloud:google-cloud-texttospeech'
    ```
   
 - Maven
    ```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.google.cloud</groupId>
                <artifactId>libraries-bom</artifactId>
                <version>26.1.4</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>com.google.cloud</groupId>
            <artifactId>google-cloud-texttospeech</artifactId>
        </dependency>
    </dependencies>
    ```

<br>
<br>

## STT 의존성 추가

- Gradle
   ```
   implementation platform('com.google.cloud:libraries-bom:26.1.4')
   implementation 'com.google.cloud:google-cloud-speech'
   ```

- Maven
   ```
   <dependencyManagement>
      <dependencies>
         <dependency>
            <groupId>com.google.cloud</groupId>
            <artifactId>libraries-bom</artifactId>
            <version>26.1.3</version>
            <type>pom</type>
            <scope>import</scope>
         </dependency>
      </dependencies>
   </dependencyManagement>

   <dependencies>
      <dependency>
         <groupId>org.json</groupId>
         <artifactId>json</artifactId>
         <version>20220924</version>
      </dependency>
      <dependency>
         <groupId>com.google.cloud</groupId>
         <artifactId>google-cloud-speech</artifactId>
      </dependency>
   </dependencies>  
   ```
  
<br>
<br>

## STT

- 1분 이상의 오디오는 Google Cloud Storage에 파일을 올려 이용해야함.
- [참고 블로그](https://since.tistory.com/29)