package hello.proxy.postprocesser;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class BeanPostProcessorTest {
    @Test
    void basicConfig(){
        //이거 자체가 스프링 컨테이너
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        //beanA이름으로 beanB가 등록됨
        B a = applicationContext.getBean("beanA", B.class);
        a.helloB();

        //B는 빈으로 등록 안함
        //Assertions.assertThrows(NoSuchBeanDefinitionException.class,() -> applicationContext.getBean(B.class));
    }

    @Slf4j
    @Configuration
    static class BasicConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }

        @Bean
        public AtoBPostProcessor atoBPostProcessor(){
            return new AtoBPostProcessor();
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

    @Slf4j
    static class AtoBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={}, bean={}",beanName,bean);
            if(bean instanceof A){
                return new B();
            }
            return bean;
        }
    }
}
