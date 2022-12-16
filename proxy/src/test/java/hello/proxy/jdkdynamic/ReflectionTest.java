package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
* 자바의 리플랙션
* but! 사용안하는게 좋음
* 컴파일단계에서 오류를 찾기 어렵기 때문.
* */
@Slf4j
@SpringBootTest
public class ReflectionTest {
    @Test
    void reflection(){
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result === {}",result1);

        log.info("start");
        String result2 = target.callB();
        log.info("result === {}",result2);
    }

    @Test
    void reflection1() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1 = {}",result1);

        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallA.invoke(target);
        log.info("result2 = {}",result1);
    }

    @Test
    void reflection2() throws Exception {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA,target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB,target);
    }

    private void dynamicCall(Method method,Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result === {}",result);
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("call A");
            return "A";
        }

        public String callB(){
            log.info("call B");
            return "B";
        }
    }
}
