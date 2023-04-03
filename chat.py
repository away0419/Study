import os
from dotenv import load_dotenv
import openai

# .env 파일 로드
load_dotenv()

# 환경 변수에 저장된 key 값 사용
chat_api_key = os.getenv("CHAT_GPT_API_KEY")

# API key 설정
openai.api_key = chat_api_key

# 모델 - GPT 3.5 Turbo 선택
model = "gpt-3.5-turbo"

# 질문 작성하기
query = "effective java item 7 자세히 설명해줘"

# 메시지 설정하기
messages = [
        {"role": "system", "content": "You are a helpful assistant."},
        {"role": "user", "content": query}
]

# ChatGPT API 호출하기
response = openai.ChatCompletion.create(
    model=model,
    messages=messages
)

# 응답 콘텐츠 가져오기
answer = response['choices'][0]['message']['content']

print(answer)

""" 결과
Effective Java의 Item 7 "불필요한 객체 생성을 피하라"는 자바의 성능 최적화를 위한 중요한 아이템 중 하나입니다.

이 아이템은 자바에서 객체 생성에 대한 비용이 높다는 점을 기반으로 합니다. 객체 생성은 다른 작업보다 더 많은 메모리 할당과 가비지 컬렉션을 유발합니다. 따라서 불필요한 객체 생성을 피하면 프로그램의 성능을 개선할 수 있습니다.

여기서 불필요한 객체 생성이란, 불필요한 메모리 할당을 유발하는 객체 생성을 의미합니다. 예를 들어, 문자열을 연결할 때 새로운 문자열 객체를 생성하는 것보다 StringBuilder 등의 임시 객체를 활용해서 문자열을 연결하는 것이 성능에 좋습니다.

또한, 생성하려는 객체가 불변 (immutable) 객체일 경우, 불필요하게 매번 새로운 객체를 생성하는 것보다는 미리 정의된 객체를 사용하는 것이 좋습니다. 예를 들어, java.lang.Integer.valueOf()와 같은 메서드에서는 클래스 캐시를 사용하여 미리 정의된 객체를 반환합니다.

마지막으로, 생성자를 인스턴스화할 때, 반드시 필요한 경우가 아니라면 불필요한 객체 생성을 피해야 합니다. 예를 들어, 코드에서 null 확인을 위한 객체 생성을 피하는 것이 좋습니다.

이렇게 불필요한 객체 생성을 피하는 것은 자바 프로그램의 성능 향상에 크게 기여하는 요소 중 하나입니다. 따라서 객체 생성에 대한 비용을 고려하면서, 필요한 경우에만 객체를 생성하도록 하면 좋습니다.
"""