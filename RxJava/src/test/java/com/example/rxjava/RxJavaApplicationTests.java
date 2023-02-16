package com.example.rxjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest 가 없어도 테스트가 실행되는 이유 : Junit5
@Slf4j
class RxJavaApplicationTests {

    @Test
    void contextLoads() { //Junit5 는 public, private 가 없어도 됨
        log.info("과연 테스트가 실행될것인가!!!");
    }

}
