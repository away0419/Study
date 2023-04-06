from google.cloud import texttospeech

# 클라이언트 생성
client = texttospeech.TextToSpeechClient()

# 변환할 텍스트 입력
synthesis_input = texttospeech.SynthesisInput(text="Hello, World!")

# 목소리 언어, 성별 설정
voice = texttospeech.VoiceSelectionParams(
    language_code="en-US", ssml_gender=texttospeech.SsmlVoiceGender.NEUTRAL
)

# 오디오 파일 설정
audio_config = texttospeech.AudioConfig(
    audio_encoding=texttospeech.AudioEncoding.LINEAR16
)

# 텍스트, 목소리, 오디오를 포함하여 요청을 보내고 응답을 받음
response = client.synthesize_speech(
    input=synthesis_input, voice=voice, audio_config=audio_config
)

# 오디오 파일 생성
with open("output.mp3", "wb") as out:
    # Write the response to the output file.
    out.write(response.audio_content)
    print('Audio content written to file "output.mp3"')
