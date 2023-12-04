> ## TTS-STT

- TTS (Text To Speech) : 주어진 문자열을 음성으로 바꾸는 기술.
- STT (Speech To Text) : 주어진 음성을 문자열로 바꾸는 기술.

<br>
<br>

> ## Google Cloud TTS-STT

- Google Cloud API를 사용하려면 Json을 다운 받아 환경 변수에 등록하거나, API Key를 이용해 Google과 통신하여 Json 정보를 받아와야 함.

<br>
<br>

> ## Google Cloud 가입하기

- [Google Cloud](https://cloud.google.com/text-to-speech/docs/samples?hl=ko)
- 해당 링크에 들어가 무료로 시작하기 클릭
- 카드 등록 후 프로젝트 생성

</br>
</br>

> ## Google Cloud Text-to-Speech API 활성화하기

- 프로젝트 선택
- "API 및 서비스" > "라이브러리"로 이동
- "Google Cloud Text-to-Speech API"를 검색하고 활성화 (STT도 같이 나오니 필요할 경우 활성화)

</br>
</br>

> ## 서비스 계정 생성하기

- 프로젝트를 선택
- "API 및 서비스" > "사용자 인증 정보"로 이동
- "+사용자 인증 정보 만들기" > "서비스 계정" 만들기

<br>
<br>

> ## 키 Json으로 다운받기

- "사용자 인증 정보" > "서비스 계정 관리" 클릭
- "세부정보" > "키" > "키 추가" > "새 키 만들기" > "Json"

<br>
<br>

> ## 해당 Json 환경 변수에 등록

- "실행"으로 "sysdm.cpl" 열기
- GOOGLE_APPLICATION_CREDENTIALS=/Users/.../Speech-01-xxxxx.json 등록
- 만약 인텔리제이를 사용한다면 환경 변수 설정 후 인텔리제이 재시작 해야 불러옴
