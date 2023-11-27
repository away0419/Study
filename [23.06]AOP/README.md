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

1. AspectJ
   - [.aj 파일]을 이용한 AspectJ Compiler를 추가로 사용하여 컴파일 시점이나 로드 시점에 적용.
   - Spring AOP에 비해 사용 방법이 다양하고 내부 구조가 굉장히 복잡함.
   - 모든 포인트 컷 지원.
   - 3가지 유형의 Weaving 제공.
