> ## AOP란 무엇인가

- 관점 지향 프로그래밍.
- 어떤 로직을 핵심적인 관점, 부가적인 관점으로 나누어 각각 모듈화 하고 재사용 하는 것.
  - 핵심 : 비즈니스 로직
  - 부가 : 로깅, DB연결, 파일 입출력

<br/>
<br/>

> ## AOP 관련 용어

- Aspect : 흩어진 관심사를 모듈화 한 것. (주로 부가 기능)
- Target : Aspect를 적용하는 곳. (클래스, 메스드 등)
- Advice : 실질적 부가 기능을 담은 구현체.
- Join Point : Advice가 적용될 위치 혹은 끼어들 수 있는 지점. (Spring의 Join Point는 메서드 실행 시점을 의미)
- Point Cut : Point의 상세한 스펙을 정의한 것. (Advice 적용될 지점을 상세히 정하는 것.)
- Weaving : Point Cut에 의해 결정된 Target의 Join Point에 Advice 삽입하는 과정.

<br/>
<br/>

> ## AOP 적용 방식 (Weaving 반영 시점)

1.  컴파일 타임 적용
    - 컴파일 시점 적용. (Compile-Time-Weaving)
      - 소스 작성 후 Weaving 된 클래스 파일을 AspectJ Compiler로 생성.
      - 컴파일 시점에 하나의 바이트코드로 만들기 때문에 컴파일 이후 성능에 영향을 주지 않음.
      - Lombok과 같이 컴파일 과정에서 조작하는 플러그인과 높은 확률로 충돌하여 컴파일 오류가 발생할 수 있음.
    - 컴파일 후 적용 (Post-Compile-Weaving)
      - 이미 존재하는 class 파일, jar 파일에 Weaving 함.

<br/>

2. 로드 타임 적용 (Load-Time-Weaving)
   - 컴파일한 뒤, 클래스를 로딩 시점에(JVM 로드) 클래스 정보를 변경하는 방법.
   - CTW와 다르게 실행되기 전까지 바이트코드를 변경하지 않아 컴파일 시간이 짧으나, 런타임 성능에 영향을 줄 수 있음.
   - 성능 하락 방지를 위해 추가적인 옵션 설정 필요.

<br/>

3. 런타임 적용
   - Spring AOP가 사용하는 방법. (Spring Bean Proxy Pattern 이기 때문)
   - Bean을 등록할 때, Proxy Bean에 Aspect 추가하는 방법.
   - 앱 성능에 영향을 줄 수 있음.

<br/>
<br/>

> ## AOP 라이브러리

1. Spring AOP

   - 프록시 패턴 기반의 AOP 구현체.
     - CGlib등 바이트코드 조작을 이용한 다이나믹 프록시를 사용.
   - 런타임 시점에 적용되며, 프록시 객체를 이용하므로 앱 성능에 영향을 줄 수 있음.
   - 일반적인 문제 해결을 위해 Spring IoC에서 제공하는 간편한 AOP기능.
   - 완벽한 AOP 솔루션이 아님. Spring 컨테이너가 관리하는 Bean에만 AOP 적용 가능.
   - 메소드 실행 Point Cut만 지원.
   - 각종 라이브러리와 호환성이 뛰어남. (Lombok)

<br/>

2. AspectJ
   - [.aj 파일]을 이용한 AspectJ Compiler를 추가로 사용하여 컴파일 시점이나 로드 시점에 적용.
   - Spring AOP에 비해 사용 방법이 다양하고 내부 구조가 굉장히 복잡함.
   - 모든 포인트 컷 지원.
   - 3가지 유형의 Weaving 제공.

<br/>
<br/>

> ## HTTP 상태 코드

- 요청 처리 결과를 보여주기 위한 코드.
- 100 ~ 500 까지 정의 되어있음.
  - 1xx : 임시 응답으로 현재 요청까지는 처리 되었으니 계속 진행하라는 의미.
  - 2xx : 요청에 대한 응답을 성공적으로 완수했다는 의미.
  - 3xx : 성공적인 응답을 위해 추가 동작이 필요하다는 의미.
  - 4xx : 요청이 잘못된 경우를 의미. (없는 페이지 요청 등)
  - 5xx : 서버에서 오류가 발생한 경우를 의미.

<details>
  <summary>1xx</summary>

| 상태 코드 | 상태 텍스트         | 의미                                                                                                    |
| --------- | ------------------- | ------------------------------------------------------------------------------------------------------- |
| 100       | Continue            | 클라이언트는 요청 헤더에 ‘Expect: 100-continue’를 보내고 서버는 이를 처리할 수 있으면 이 코드로 응답    |
| 101       | Switching Protocols | 프로토콜을 HTTP 1.1에서 업그레이드할 때 Upgrade 응답 헤더에 표시.                                       |
| 102       | Processing          | 서버가 처리하는 데 오랜 시간이 예상되어 클라이언트에서 타임 아웃이 발생하지 않도록 이 응답 코드를 보냄. |

</details>

<details>
  <summary>2xx</summary>

| 상태 코드 | 상태 텍스트                   | 의미                                                      |
| --------- | ----------------------------- | --------------------------------------------------------- |
| 200       | OK                            | 서버가 요청을 성공적으로 처리하였다.                      |
| 201       | Created                       | 요청이 처리되어서 새로운 리소스가 생성되었다              |
| 202       | Accepted                      | 요청은 접수하였지만, 처리가 완료되지 않았다               |
| 203       | Non-Authoritative Information | 응답 헤더가 오리지널 서버로부터 제공된 것이 아니다        |
| 204       | No Content                    | 처리를 성공하였지만, 클라이언트에게 돌려줄 콘텐츠가 없다. |
| 205       | Reset Content                 | 처리를 성공하였고 브라우저의 화면을 리셋하라.             |
| 206       | Partial Content               | 콘텐츠의 일부만을 보낸다.                                 |
| 207       | Multi-Status                  | 처리 결과의 스테이터스가 여러 개이다.                     |

</details>

<details>
  <summary>3xx</summary>

| 상태 코드 | 상태 텍스트        | 의미                                               |
| --------- | ------------------ | -------------------------------------------------- |
| 300       | Multiple Choices   | 선택 항목이 여러 개 있다.                          |
| 301       | Moved Permanently  | 지정한 리소스가 새로운 URI로 이동하였다.           |
| 302       | Found              | 요청한 리소스를 다른 URI에서 찾았다.               |
| 303       | See Other          | 다른 위치로 요청하라.                              |
| 304       | Not Modified       | 마지막 요청 이후 요청한 페이지는 수정되지 않았다.  |
| 305       | Use Proxy          | 지정한 리소스에 액세스하려면 프록시를 통해야 한다. |
| 307       | Temporary Redirect | 임시로 리다이렉션 요청이 필요하다.                 |

</details>

<details>
  <summary>4xx</summary>

| 상태 코드 | 상태 텍스트                        | 의미                                                                    |
| --------- | ---------------------------------- | ----------------------------------------------------------------------- |
| 400       | Bad Request                        | 요청의 구문이 잘못되었다.                                               |
| 401       | Unauthorized                       | 지정한 리소스에 대한 액세스 권한이 없다.                                |
| 402       | Payment Required                   | 지정한 리소스를 액세스하기 위해서는 결제가 필요하다.                    |
| 403       | Forbidden                          | 지정한 리소스에 대한 액세스가 금지되었다.                               |
| 404       | Not Found                          | 지정한 리소스를 찾을 수 없다.                                           |
| 405       | Method Not Allowed                 | 요청한 URI가 지정한 메소드를 지원하지 않는다.                           |
| 406       | Not Acceptable                     | 클라이언트가 Accept-\* 헤더에 지정한 항목에 관해 처리할 수 없다.        |
| 407       | Proxy Authentication Required      | 클라이언트는 프록시 서버에 인증이 필요하다.                             |
| 408       | Request Timeout                    | 요청을 기다리다 서버에서 타임아웃하였다.                                |
| 409       | Conflict                           | 서버가 요청을 수행하는 중에 충돌이 발생하였다.                          |
| 410       | Gone                               | 지정한 리소스가 이전에는 존재하였지만, 현재는 존재하지 않는다.          |
| 411       | Length Required                    | 요청 헤더에 Content-Length를 지정해야 한다.                             |
| 412       | Precondition Failed                | If-Match와 같은 조건부 요청에서 지정한 사전 조건이 서버와 맞지 않는다.  |
| 413       | Request Entity Too Large           | 요청 메시지가 너무 크다.                                                |
| 414       | Request-URI Too Large              | 요청 URI가 너무 길다.                                                   |
| 415       | Unsupported Media Type             | 클라이언트가 지정한 미디어 타입을 서버가 지원하지 않는다.               |
| 416       | Range Not Satisfiable              | 클라이언트가 지정한 리소스의 범위가 서버의 리소스 사이즈와 맞지 않는다. |
| 417       | Expectation Failed                 | 클라이언트가 지정한 Expect 헤더를 서버가 이해할 수 없다.                |
| 422       | Unprocessable Entity               | 클라이언트가 송신한 XML이 구문은 맞지만, 의미상 오류가 있다.            |
| 423       | Locked                             | 지정한 리소스는 잠겨있다.                                               |
| 424       | Failed Dependency                  | 다른 작업의 실패로 인해 본 요청도 실패하였다.                           |
| 426       | Upgraded Required                  | 클라이언트의 프로토콜의 업그레이드가 필요하다.                          |
| 428       | Precondition Required              | If-Match와 같은 사전조건을 지정하는 헤더가 필요하다.                    |
| 429       | Too Many Requests                  | 클라이언트가 주어진 시간 동안 너무 많은 요청을 보냈다.                  |
| 431       | Request Header Fields Too Large    | 헤더의 길이가 너무 크다.                                                |
| 444       | Connection Closed Without Response | (NGINX) 응답을 보내지 않고 연결을 종료하였다.                           |
| 451       | Unavailable For Legal Reasons      | 법적으로 문제가 있는 리소스를 요청하였다.                               |

</details>

<details>
  <summary>5xx</summary>

| 상태 코드 | 상태 텍스트                | 의미                                                                                                  |
| --------- | -------------------------- | ----------------------------------------------------------------------------------------------------- |
| 500       | Internal Server Error      | 서버에 에러가 발생하였다.                                                                             |
| 501       | Not Implemented            | 요청한 URI의 메소드에 대해 서버가 구현하고 있지 않다.                                                 |
| 502       | Bad Gateway                | 게이트웨이 또는 프록시 역할을 하는 서버가 그 뒷단의 서버로부터 잘못된 응답을 받았다.                  |
| 503       | Service Unavailable        | 현재 서버에서 서비스를 제공할 수 없다.                                                                |
| 504       | Gateway Timeout            | 게이트웨이 또는 프록시 역할을 하는 서버가 그 뒷단의 서버로부터 응답을 기다리다 타임아웃이 발생하였다. |
| 505       | HTTP Version Not Supported | 클라이언트가 요청에 사용한 HTTP 버전을 서버가 지원하지 않는다.                                        |
| 507       | Insufficient Storage       | 서버에 저장 공간 부족으로 처리에 실패하였다.                                                          |

</details>
