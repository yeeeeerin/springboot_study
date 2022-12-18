package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
@SpringBootTest
public class CglibTest {
    @Test
    void chlib(){
        ConcreteService target = new ConcreteService();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        /* enhancer.setCallback에 TimeMethodInterceptor를 넣을 수 있는 이유?
         * -> TimeMethodInterceptor의 MethodInterceptor 가  callback을 상속받고 있기때문
         */
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("target class={}, proxy class{}", target.getClass(),proxy.getClass());

        proxy.call();
    }
}
