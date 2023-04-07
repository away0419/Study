최종 작성일 : 23.04.07

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


## 테스트 결과 검증 메서드
- assertEquals(expected, actual, message): expected 값과 actual 값이 동일한지 확인합니다. 동일하지 않은 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertNotEquals(unexpected, actual, message): unexpected 값과 actual 값이 다른지 확인합니다. 동일한 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertTrue(condition, message): 주어진 condition이 true인지 확인합니다. false인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertFalse(condition, message): 주어진 condition이 false인지 확인합니다. true인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertNull(object, message): 주어진 object가 null인지 확인합니다. null이 아닌 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertNotNull(object, message): 주어진 object가 null이 아닌지 확인합니다. null인 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertSame(expected, actual, message): expected와 actual이 동일한 객체를 참조하는지 확인합니다. 참조가 다른 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertNotSame(unexpected, actual, message): unexpected와 actual이 서로 다른 객체를 참조하는지 확인합니다. 동일한 객체를 참조하는 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertArrayEquals(expected, actual, message): 두 배열이 동일한 순서와 값을 가지고 있는지 확인합니다. 배열의 내용이 다른 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertThrows(expectedExceptionType, executable, message): 주어진 executable이 실행되었을 때 expectedExceptionType의 예외가 발생하는지 확인합니다. 예외가 발생하지 않거나 다른 종류의 예외가 발생한 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertTimeout(duration, executable, message): 주어진 executable이 지정된 duration 내에 완료되는지 확인합니다. 지정된 시간 내에 완료되지 않은 경우, 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.


- assertTimeoutPreemptively(duration, executable, message): 주어진 executable이 지정된 duration 내에 완료되는지 확인합니다. 지정된 시간 내에 완료되지 않은 경우, executable이 즉시 중단되고 선택적으로 제공하는 message가 테스트 실패 메시지로 표시됩니다.