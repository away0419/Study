import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
    @Test
    @DisplayName("더하기 테스트") // 테스트 이름
    public void addTest() {
        //given
        // 테스트할 기능을 가진 클래스 객체
        Function function = Function.functionFactory();

        //when
        // 기능의 결과 값
        int sum1 = function.add(2, 3);
        int sum2 = function.add(3, 3);

        //then
        // 예상 값과 결과 값이 동일한지 확인. 만약 틀렸을 경우 해당 text 출력
        assertEquals(5, sum1, "2 + 3 should equal 5");
        assertEquals(6, sum2, "3 + 3 should equal 5");
    }

    @Test
    @DisplayName("곱하기 테스트")
    public void multiplyTest() {
        Function function = Function.functionFactory();

        int sum1 = function.multiply(2, 3);
        int sum2 = function.multiply(3, 3);

        assertEquals(6, sum1, "2 * 3 should equal 6");
        assertEquals(9, sum2, "3 * 3 should equal 9");
    }
}
