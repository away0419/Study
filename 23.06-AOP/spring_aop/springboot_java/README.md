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
  주어진 포인트 컷에서 언제 실행될지 어노테이션을 통해 지정.<br/>
  동일한 포인트 컷에 여러 Advice가 있는 경우 다음과 같이 실행됨.<br/>
  Around -> Before -> AfterThrowing -> AfterReturning -> After -> Around<br/>
  만약 순서를 지정하고 싶다면 @Order(우선순위)를 사용.

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
  AOP를 실행할 지점을 Advice 순서에 표현식으로 알려주는 방법.<br/>
  여러 PointCut을 사용하고 싶다면 ||, && 등으로 조합 가능.

  <br/>
  <br/>

  - execution(\* \* \*.\*.\*(..))
    - 기본 표현식으로 @Around("execution()") 형태로 사용함.
    - \* : 접근제한자
    - \* : 반환타입
    - \*.\*.\* : 지정할 메소드의 패지지경로와 메소드명
    - (..) : 해당 메소드의 매개변수

  <br/>

  - within(\* \* \*.\*.\*(..))
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

  - @target(메소드 패키지명.메소드명)
    - 단독으로 사용 불가능
    - @Around(다른표현식 && "target()") 형태로 사용함.
    - 자신의 클래스와 자신의 모든 부모 클래스의 모든 메소드에서 동작

  <br/>

  - @within(메소드 패키지명.메소드명)
    - 단독으로 사용 불가능
    - @Around(다른표현식 && "within()") 형태로 사용함.
    - 자신이 포함된 클래스의 모든 메소드에서 동작

  <br/>

  - @anntation
    - @Around("@annotation(어노테이션 패키지명.클래스이름)") 형태로 사용
    - 메소드가 주어진 어노테이션을 가지고 있을 경우 동작.

  <br/>

  - bean()
    - 스프링 빈 중 주어진 이름의 조건과 맞을 경우 동작.
    - \*Repository 로 주어진 경우 Repository로 끝나는 빈 모두 적용.

  <br/>

  - this, target
    - 스프링에서 AOP를 적용하면 실제 대상 객체 대신에 프록시가 빈으로 등록되는데 이를 구분 짓고자 사용
    - this : 스프링 빈 객체 (프록시)를 대상으로 매칭
    - target : 실제 객체를 대상으로 매칭
    - 적용 타입 하나를 적오학하게 지정해야함.
    - 부모 타입 허용

</details>

<br/>

## 3. 추가 정보

<details>
  <summary>@EnableAspectJAutoProxy</summary>

스프링 컨텍스트 내에서 AspectJ AOP 프레임워크를 사용할 수 있도록 하는 어노테이션. Spring Boot는 해당 어노테이션을 사용하지 않아도 AspectJ AOP 프레임워크를 사용할 수 있음.

</details>

<details>
  <summary>JoinPoint 메소드</summary>

| 메소드              | 설명                                         |
| ------------------- | -------------------------------------------- |
| getArgs()           | 대상 메소드의 인자 목록을 반환               |
| getSignature()      | 대상 메소드의 정보를 반환                    |
| getSourceLocation() | 대상 메소드가 선언된 위치를 반환             |
| getKind()           | Advice 종류 반환                             |
| getStaticPart()     | Advice가 실행될 JoinPoint의 정적 정보를 반환 |
| getThis()           | 대상 객체를 반환                             |
| getTarget()         | 대상 객체를 반환                             |
| toString()          | JoinPoint의 정보를 문자열로 반환             |
| toShortString()     | JoinPoint의 간단한 정보를 문자열로 반환      |
| toLongString()      | JoinPoint의 자세한 정보를 문자열로 반환      |

</details>

<details>
  <summary>공통 포인트 컷 사용할 경우</summary>
  공통 포인트 컷을 사용할 경우 메소드로 만들어 사용할 수 있음.
  
  <br/>

```java
// 공통 포인트 컷
@Pointcut("execution(* com.example.springboot_java.domain.CalculateService(..))")
private void pointCut() {
}

@Pointcut("@annotation(com.example.springboot_java.annotation.LogAOP)")
private void annotationPointCut() {
}

@Around("pointCut()")
public void logAround(JoinPoint joinPoint) {
    log.info("AOP Around Execution : " + joinPoint.getSignature().getName());
}
```

</details>
