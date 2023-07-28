package com.example.springboot_java;

import com.example.springboot_java.domain.CalculateService;
import com.example.springboot_java.domain.CalculateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ExtendWith({SpringExtension.class})
class CalculateServiceTest {
    CalculateService calculateService = new CalculateServiceImpl();

    @Test
    @DisplayName("더하기 테스트")
    void addSuccess() throws Exception {
        int rs =calculateService.doAdd("1", "2");
        Assertions.assertEquals(rs, 3);
    }

    @Test
    @DisplayName("빼기 테스트")
    void minusSuccess() throws  Exception{
        int rs = calculateService.doMinus("1", "2");
        Assertions.assertEquals(rs, -1);
    }

}
