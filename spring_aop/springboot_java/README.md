최종 작성일 : 2023.07.26.</br>

# SpringAOP

## 1. 의존성 추가

```gradlew
dependencies {
	implementation('org.springframework.boot:spring-boot-starter-aop')
}
```

<br/>

## 2. Aspect 클래스 작성

- @Aspect, 빈 등록
- <details>
  <summary>Advice 순서 지정</summary>
  주어진 포인트 컷에서 언제 실행될지 어노테이션을 통해 지정

  <br/>
  <br/>

  - @Around
    - 뒤에 나올 4가지 상태를 모두 포함하며 원하는 시점에 원하는 작업 가능.
    - 메서드 호출 전후 작업 명시 가능
    - 조인 포인트 실행 여부 선택 가능
    - 반환값 자체 조작 가능
    - 예외 자체를 조작 가능
    - 조인 포인트를 여러번 실행 가능

  <br/>

  - @Before
    - 타겟 실행 전에 끼어들어 작업 수행
    - 메소드 제어, 데이터 가공은 불가능

  <br/>

  - @After
    - 타겟 실행 후에 끼어들어 작업 수행
    - 메소드 제어, 데이터 가공 불가능

  <br/>

  - @AfterReturning
    - 타겟이 정상적으로 실행 완료된 경우 끼어들어 작업 수행
    - 리턴 값 확인 가능하나 메소드 제어, 데이터 가공 불가능

  <br/>

  - @AfterThrowable
    - 타겟이 예외를 발생시킨 경우 끼어들어 작업 수행
    - 예외 값 확인 가능하나 메소드 제어, 데이터 가공 불가능

  <br/>
  </details>

- <details>
  <summary>PointCut 지정</summary>
  AOP를 실행할 지점을 Advice 순서에 표현식으로 알려주는 방법

  <br/>
  <br/>

  - execution(\* \* _._.\*(..))
    - 기본 표현식으로 @Around("execution()") 형태로 사용함.
    - \* : 접근제한자
    - \* : 반환타입
    - \*.\*.\* : 지정할 메소드의 패지지경로와 메소드명
    - (..) : 해당 메소드의 매개변수

  <br/>

  - within(\* \* _._.\*(..))
    - @Around("within()") 형태로 사용함.
    - 타입이 정확하게 맞아야 동작.
    - 따라서, 상위 타입으로 하위 타입 매칭 불가능(service로 설정할 경우 serviceimpl에서 동작 안함)

  <br/>

  - args(\*)
    - args 단독으로 사용하면 안됨.
    - @Around(다른표현식 && "args()") 형태로 사용함.
    - 파라미터 타입이 부모, 하위 일 경우에도 동작.
    - \* : 파라미터 타입

  <br/>

  - @target
    - 단독으로 사용 불가능
    - 자신의 클래스와 자신의 모든 부모 클래스의 모든 메소드에서 동작

  <br/>

  - @within
    - 단독으로 사용 불가능
    - 자신이 포함된 클래스의 모든 메소드에서 동작

</details>

<br/>

## 3.
