최종 작성일 : 23.04.13

JUnit5 테스트

## 의존성 추가

- gradle
    ```
    dependencies {
        testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
        testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
    }
    ```

- maven

    ```
    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.8.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    ```
<br>
<br>

## 자주 사용하는 어노테이션

어노테이션|설명
---|---
@Test|테스트 메소드를 나타내는 어노테이션입니다. 필수로 작성되어야 합니다.
@BeforeEach|각 테스트 메소드 시작 전에 실행되어야 하는 메소드에 써줍니다.
@AfterEach|각 테스트 메소드 종료 후에 실행되어야 하는 메소드에 써줍니다.
@BeforeAll|테스트 시작 전에 실행되어야 하는 메소드에 써줍니다. (static 메소드여야만 함)
@AfterAll|테스트 종료 후에 실행되어야 하는 메소드에 써줍니다. (static 메소드여야만 함)
@Disabled|실행되지 않아야 하는 테스트 메소드에서 써줍니다.
@DisplayName|클래스, 메서드에 사용자가 직접 테스트명 설정 가능

<br>
<br>

## 기타 어노 테이션
### @DisplayNameGeneration
- 기존 클래스, 메서드명에서 변형한 테스트명으로 설정
- DisplayNameGenerator 내부 클래스를 이용

    내부 클래스|설명
    --|--
    Standard|기존 클래스, 메소드 명을 사용합니다. (기본값)
    Simple|괄호를 제외시킵니다.
    ReplaceUnderscores|_(underscore) 를 공백으로 바꿉니다.
    IndicativeSentences|클래스명 + 구분자(", ") + 메소드명으로 바꿉니다.

<br>

### @IndicativeSentencesGeneration
- IndicativeSentences의 구분자를 커스텀하게 사용할 수 있게해줍니다.

    파라미터명|설명
    --|--
    separator|구분자 (기본값 : ", ")
    generator|정의된 DisplayNameGenerator 중 하나 사용

<br>

### @Tag
- 테스트 코드를 구분지어 태깅하고 원하는 태그만 필터링해서 테스트할 수 있게해줌.
- 필터링을 하려면 추가 설정이 필요.


    파라미터명|설명
    --|--
    value|태그명
    
<br>

### @RepeatedTest
- RepetitionInfo 인자를 받아 테스트를 반복.
- RepetitionInfo 인자를 사용하려면 반드시 해당 어노테이션이 필요함.

    파라미터명|설명
    --|--
    value|반복 횟수 (반드시 0보다 커야함) (필수)
    name|반복할 때 나타나는 테스트명
    
<br>

### @ParameterizedTest
- 파라미터를 넣어서 테스트를 반복.

    파라미터명|설명
    --|--
    name|@DisplayName 설정
    DISPLAY_NAME_PLACEHOLDER|@DisplayName과 동일
    INDEX_PLACEHOLDER|현재 실행 인덱스
    ARGUMENTS_PLACEHOLDER|현재 실행된 파라미터 값
    ARGUMENTS_WITH_NAMES_PLACEHOLDER|현재 실행된 파라미터명 + "=" + 값
    DEFAULT_DISPLAY_NAME|"[" + INDEX_PLACEHOLDER + "] " + ARGUMENTS_WITH_NAMES_PLACEHOLDER

<br>


- 단독으로 사용되지 않으며 어떤 파라미터를 사용하는지에 관한 어노테이션을 추가로 선언해야함.

- @valueSource
    - 댜양한 타입의 파라미터를 배열로 받아 사용할 수 있게 해줌.
    - 각 타입명에 'S'를 붙힌 것이 파라미터명
    - 파라미터 인자는 1개

- @NullSource
    - 메서드 인자에 null 사용
    - 메서드 인자가 1개일 때만 사용 가능
    - 기본 타입에는 사용 불가

- @EmptySource
    - 메서드 인자에 빈 값 객체 사용
    - 메서드 인자가 1개일 때만 사용 가능

- @NullAndEmptySource
    - @NullSource와 @EmptySource를 합한 것

- @EnumSource
    - enum에 정의된 상수들을 테스트하기 위한 어노테이션
    
    파라미터|설명
    --|--
    value|테스트할 Enum 클래스 (기본값 : NullEnum.class)
    names|문자열(정규식)
    mode|names를 이용하여 검색 

- @MethodSource
    - factory 메서드가 리턴해주는 값을 가지고 반복 테스트하는 어노테이션
    - 반드시 static. 단, 테스트 클래스에 @TestInstance(Lifecycle.PER_CLASS)가 있다면 필요 없음.
    - 인자 없어야함.
    - Stream 타입으로 리턴해야함.
    
    파라미터|설명
    --|--
    value|factory 메소드 명

- @CvsSource
    - CSV 현식의 데이터로 반복 테스트

    파라미터|설명
    --|--
    value|CVS 형식의 데이터
    delimiter|delimiter를 변경 (char 형)
    delimiterString|delimiter를 변경 (String 형)
    emptyValue|CVS 데이터 중 빈 값인 경우 대체되는 값
    nullValues|CVS 데이터 중 null 값으로 대체할 값

- @CvsFileSource
    - cvs 파일을 읽어서 테스트할 수 있게 해주는 어노테이션

    파라미터|설명
    --|--
    resources|.cvs 파일 경로
    files|.cvs 파일 경로
    encoding|파일 인코딩 값
    lineSeparator|줄 바꿈 구분자
    delimiter|delimiter를 변경 (char 형)
    delimiterString|delimiter를 변경 (String 형)
    numLinesToSkip|cvs 파일 라인 스킵 수
    emptyValue|CVS 데이터 중 빈 값인 경우 대체되는 값
    nullValues|CVS 데이터 중 null 값으로 대체할 값

- @ArgumentSource
    - 정해진 데이터 주입 방법말고 커스텀하게 주입 데이터 값을 정할 수 있음.

    파라미터|설명
    --|--
    value|데이터 주입 방법을 정의한 클래스


<br>

### @TestInstance
- Junit은 설정된 테스트 단위로 테스트 객체를 만듬. 이를 테스트 인스턴스라 함.
- 따라서 필드 변수를 메서드가 공유하지 못함.
- 이를 해결하고자 테스트 인스턴스의 생성 단위를 변경하기 위한 어노테이션임.
- 설정을 통해 기본 값 변경 가능.
- 생성 단위를 변경하여도 메서드 실행 순서를 보장 하지는 않기 때문에 예상과 다른 결과가 나올 수 있음.

    파라미터|설명
    --|--
    value|테스트 인스턴스 생성 단위 설정. (PER_METHOD: 기본 값, PER_CLASS: 클래스 단위)

<br>

### @TestMethodOrder
- 테스트 순서를 정해줌.
- MethodOrder 내부 클래스 이용함.

    파라미터|설명 (내부클래스 : 정렬 기준)
    --|--
    value|MethodName : 메소드명, DisplayName : @DisplayName, OrderAnnotation : @Order(n), Random : 랜덤

<br>

### @TestMethodOrder 커스텀
- 개발자가 원하는 형태의 정렬로 구현하는 방법
- MethodOrder를 implements해서 구현
- 이후 해당 클래스를 value에 넣어서 사용


<br>
<br> 


## Assertions
- 개발자가 테스트 하고 싶은 인자 값을 넣었을 때 예상한 결과가 나오는 지 테스트 할 때 사용

    메서드|설명
    ---|---
    assertEquals(expected, actual, message)|expected 값과 actual 값이 동일한지 확인합니다. 동일하지 않은 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertNotEquals(unexpected, actual, message)|unexpected 값과 actual 값이 다른지 확인합니다. 동일한 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertTrue(condition, message)|주어진 condition이 true인지 확인합니다. false인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertFalse(condition, message)|주어진 condition이 false인지 확인합니다. true인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertNull(object, message)|주어진 object가 null인지 확인합니다. null이 아닌 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertNotNull(object, message)|주어진 object가 null이 아닌지 확인합니다. null인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertSame(expected, actual, message)|expected와 actual이 동일한 객체를 참조하는지 확인합니다. 참조가 다른 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertNotSame(unexpected, actual, message)|unexpected와 actual이 서로 다른 객체를 참조하는지 확인합니다. 동일한 객체를 참조하는 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertArrayEquals(expected, actual, message)|두 배열이 동일한 순서와 값을 가지고 있는지 확인합니다. 배열의 내용이 다른 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertThrows(expectedExceptionType, executable, message)|주어진 executable이 실행되었을 때 expectedExceptionType의 예외가 발생하는지 확인합니다. 예외가 발생하지 않거나 다른 종류의 예외가 발생한 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertTimeout(duration, executable, message)|주어진 executable이 지정된 duration 내에 완료되는지 확인합니다. 지정된 시간 내에 완료되지 않은 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.
    assertTimeoutPreemptively(duration, executable, message)|주어진 executable이 지정된 duration 내에 완료되는지 확인합니다. 지정된 시간 내에 완료되지 않은 경우, executable이 즉시 중단되고 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


<br>
<br>

## Assumptions
- 개발자가 인자 값을 정확히 모를 때 if와 같은 용로도 사용

    메소드명|설명
    --|--
    assumeTrue|테스트가 실패하면 에러 발생
    assumeFalse|테스트가 성공하면 에러 발생
    assumingThat(boolean, executable)|첫 번째 인자가 True면 두 번째 인자로 들어온 함수 실행. 첫 번째 인자 값이 false 인 경우에도 테스트를 스킵하지 않고 다음 코드를 진행합니다.


## Hamcrest
- assertions, assumptions 를 좀 더 가독성있고 편하게 쓸 수 있도록 도와주는 라이브러리 
- Matcher 클래스를 이용하여 첫 번째 인자로 들어온 값을 검증.

    메소드명|설명
    --|--
    assertThat|비교 메소드

- Matcher
    
    메소드명|설명
    --|--
    anything()|항상 성공
    describedAs(String description, Matcher matcher,  java.lang.Object... values)|decorator로 정의한 Matcher에 별도 설명이 필요할 경우 사용
    is|decorator로 가독성을 높혀주기 위해 사용. 그냥 사용하면 = equalTo랑 동일합니다
    allOf(Matcher<? super T>... matchers)|모든 Matcher에 적합한 경우 성공
    anyOf(Matcher<? super T>... matchers)|Matcher 중 한 개만 적합해도 성공
    not(Matcher<? super T> matcher)|Matcher 에 적합하면 경우 실패
    equalTo|Object.equals 테스트
    hasToString|Object.toString 테스트
    instanceOf|테스트 대상의 타입 비교
    isCompatibleType|타입 간 호환이 되는지 비교
    notNullValue, nullValue|null인지 아닌지
    sameInstance|같은 Object인지 테스트
    hasProperty|자바빈즈 필드 테스트
    array|배열 항목 별 테스트
    hasEntry|Map 테스트, 테스트 key 값과 key 값의 value 비교
    hasKey, hasValue|key, value 존재 여부 테스트
    hasItem, hasItems|iterator 에 item 존재 여부 테스트
    hasItemInArray|array에 값 존재 여부 테스트
    closeTo(double operand, double error)|테스트할 값과 operand의 오차가 error 이하인지 테스트
    greaterThan, greaterThanOrEqualTo|초과, 이상 테스트
    lessThan, lessThanOrEqualTo|미만, 이하 테스트
    equalToIgnoringCase|대소문자 무시 후 문자열 테스트
    containsString|문자열 포함 여부 테스트
    endsWith|~으로 끝나는지 테스트
    startsWith|~으로 시작하는지  테스트

<br>
<br>

## AssertJ
- Hamcrest와 거의 비슷
- 메서드 체이닝 방식 사용 (Hamcrest 보다 편하게 사용 가능)

     메소드명|설명
    --|--
    assertThat(T actual)|테스트 시작 메소드




