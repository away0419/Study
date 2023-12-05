> ## 소프트웨어 아키텍처

- 소프트웨어 시스템 및 개발 프로젝트의 청사진 역할을 하며 설계 팀이 실행하는데 필요한 작업을 제시함.
- 소프트웨어의 내부적인 질을 높일 수 있음.
- 검증된 구조로 개발할 수 있어 안정적인 개발 가능.
- 시행 착오를 줄여 비용을 줄일 수 있음.
- 손쉽게 유지 보수 가능.

<br/>
<br/>

> ## 기본 원리

- 모듈화
  - 시스템의 기능들을 모듈 단위로 나누는 것
  - 너무 많이 나눌 경우 통합 비용이 커짐.
  - 너무 적게 나눌 경우 개발 비용이 커짐.
- 추상화
  - 전체적이고 포괄적인 개념 설계 후 차례로 세분화 / 구체화.
- 단계적 분해
  - 문제를 상위의 중요 개념으로부터 하위 개념으로 구체화시키며 분할
- 정보 은닉
  - 한 모듈 내부에 포함된 절차와 자료를 숨기며 독립적 수행을 가능캐 해, 다른 모듈이 접근하지 못하게 함.

<br/>
<br/>

> ## 소프트웨어 아키텍쳐 패턴 종류

- 일반적으로 많이 사용하는 아키텍쳐는 다음과 같음.

<details>
  <summary>Layered Pattern</summary>

- 각각의 서비스 시스템들이 계층 구조를 이룸.
- 상위 계층은 하위 계층의 서비스 제공자.
- 하위 계층은 상위 계층의 클라이언트.
- ex) OSI 계층 모델

</details>

<details>
  <summary>Client-Server Pattern</summary>

- 하나의 서버와 다수의 클라이언트로 구성됨.
- 서버 컴포넌트는 다수의 클라이언트 컴포넌트에게 요청이 들어올 때마다 서비스 제공.
- 서버는 계속 클라이언트로부터 요청을 대기 해야 함.
- ex) 이메일, 문서 공유 및 은행 등의 온라인 어플리케이션

</details>

<details>
  <summary>Master-Slave Pattern</summary>

- 마스터 컴포넌트가 슬레이브 컴포넌트들에게 작업을 분산 시킴.
- 슬레이브의 반환 값으로부터 최종 결과값 계산.
- ex) 컴퓨터 시스템에서 버스와 연결된 주변 창치, 병렬 컴퓨터, 장애 허용 시스템.

</details>

<details>
  <summary>Pipe-Filter Pattern</summary>

- 데이터 스트림을 생성하고 처리함.
- 처리 과정은 filter에서 이루어지며 처리되는 데이터는 파이프를 통해 흐름.
- 버퍼링 또는 동기화 목적으로 사용될 수 있음.
- ex) 컴파일러, Unix shell

</details>

<details>
  <summary>Broker Pattern</summary>

- 분리된 컴포넌트들로 이루어진 분산 환경에 사용.
- 원격 서비스 실행을 통해 상호 작용 가능.
- 브로커 컴포넌트는 컴포넌트 간의 통신을 조정함.
- ex) 분산환경 시스템

</details>

<details>
  <summary>Peer-to-Peer Pattern</summary>

- 각 컴포넌트를 피어라 부름.
- 피어는 클라이언트와 서버 모두 가능.
- 파일 공유, 네트워크, 밀티미디어 프로토콜)
</details>

<details>
  <summary>Event-Bus Pattern</summary>

- 주로 이벤트를 처리.
- 이벤트 소스, 이벤트 리스너, 채널, 이벤트 버스 라는 주요 4가지 컴포넌트를 가짐.
- 소스는 이벤트 버스를 통해 특정 채널로 메세지를 발행.
- 리스너는 이전에 구독한 채널에 발행된 메시지에 대한 알림 받음. (안드로이드 개발, 알림 서비스)
</details>

<details>
  <summary>Model-View-Controller Pattern</summary>

![Alt text](image/image.png)

- 어플리케이션을 세 파트로 나눔.
  - 모델 : 핵심 기능과 데이터를 포함하고 있음.
  - 뷰 : 사용자에게 정보를 표시하는 역할. 옵저버 패턴을 이용함. (데이터의 상태 변화에 맞춰 업데이트를 하기 위함. 이로 인해 V-M 사이 의존성이 발생함.)
  - 컨트롤러 : 사용자로부터 발생한 입력 처리. model을 통해 받은 데이터를 처리하고 결과 값을 View에 반환함.
- Controller와 View는 1:N 관계.
- 가장 단순하여 여러 분야에서 사용됨.
- View와 Model 사이의 의존성이 높음. (규모가 커질수록 유지보수 어려움.)
- ex) 웹 (요즘 처럼 Back과 Front가 나뉜 경우는 MVC 패턴이라 볼 수 없음. 하나의 어플리케이션이 아니기 때문임. Rest API는 Spring MVC 프레임워크를 사용할 뿐임.)
</details>

<details>
  <summary>Model-View-Presenter Pattern</summary>

![Alt text](image/image-1.png)

- Controller 대신 Presenter 사용함.
  - Model : 핵심 기능과 데이터를 포함하고 있음.
  - View : 사용자에게 정보를 표시하는 역할. 모든 입력들이 이곳으로 들어옴.
  - Presenter : View에서 요청한 정보로 Model을 가공하여 View에 전달해주는 역할. (Model과 View의 Interface 를 가지고 있음.)
- Presenter과 View는 1:1 관계.
- M-V 의존성은 없으나 V-P 의존성이 있음. (규모가 커질수록 강해짐.)

</details>

<details>
  <summary>Model-View-ModelView Pattern</summary>

- Controller 대신 View Model 사용함.
  - Model : 핵심 기능과 데이터를 포함하고 있음.
  - View : 사용자에게 정보를 표시하는 역할. 모든 입력들이 이곳으로 들어옴.
  - View Model : View를 표현하기 위해 만든 Model.
- 코맨드 패턴과 데이터 바인딩을 이용하여 View-ViewModel 의존성 없앰.
- M-V 의존성도 없음.
- View Model과 View는 1:N 관계.
- 테스트 및 확장 용이함.
- ViewModel 설계가 쉽지 않음.
</details>

<details>
  <summary>BlackBoard Pattern</summary>

- 해결책이 명확하지 않은 문제 처리시 유용함.
- 3가지 주요 컴포넌트로 구성됨.
  - 블랙보드 : 솔루션의 객체를 포함하는 구조화된 전역 메모리.
  - 지식소스 : 자체 표현을 가진 특수 모듈
  - 제어 컴포넌트 : 모듈 선택 설정 및 실행 담당.
- 모든 컴포넌트는 블랙보드에 접근.
- 컴포넌트는 블랙보드에 추가되는 새로운 데이터 객체를 생성할 수 있음.
- 컴포넌트는 블랙보드에서 특정 종류의 데이터를 찾으며, 기존의 지식 소스와의 패턴매칭으로 데이터를 찾음.
- ex) 음성인식, 차량 식별 및 추적, 신호해석, 단백질 구조 식별

</details>

<details>
  <summary>Interpreter Pattern</summary>

- 특정 언어로 작성된 프로그램을 해석하는 컴포넌트를 설계할 때 사용함.
- 언어의 각 기호에 대해 클래스를 생성.
- ex) SQL, 통신프로토콜 정의어

</details>

<br/>
<br/>
