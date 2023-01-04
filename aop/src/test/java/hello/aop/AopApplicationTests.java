package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Slf4j
@Import(AspectV2.class) //스프링빈 등록함
class AopApplicationTests {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void contextLoads() {
    }
    @Test
    void aopTest(){
        log.info("isaop, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isaop, orderRepository={}", AopUtils.isAopProxy(orderRepository));

    }

}
