package hello.proxy.postprocesser;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class BasicTest {

    @Test
    void basicConfig(){
        //이거 자체가 스프링 컨테이너
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        //A는 빈으로 등록 가능
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        //B는 빈으로 등록 안함
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,() -> applicationContext.getBean(B.class));
    }

    @Slf4j
    @Configuration
    static class BasicConfig{
        @Bean(name = "beanAA")
        public A a(){
            return new A();
        }

    }

    @Slf4j
    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }

    @Slf4j
    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }
}
