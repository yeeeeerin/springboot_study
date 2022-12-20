package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

/*
* 실무에서는 aspectJ 표현식을 거의 사용함
* */
@SpringBootTest
@Slf4j
public class AdvisorTest {
    @Test
    void advisorTest1(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        factory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) factory.getProxy();
        proxy.save();
    }

    /*
    *
    * */
    @Test
    @DisplayName("내가 만든 포인트컷")
    void advisorTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        factory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) factory.getProxy();
        proxy.save();
        proxy.find();
    }

    /*
    *
    * */
    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        factory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) factory.getProxy();
        proxy.save();
        proxy.find();
    }

    static class MyPointCut implements Pointcut{

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMather();
        }
    }

    static class MyMethodMather implements MethodMatcher{
        private String matchName = "save";
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            log.info("포인트컷 호출 method = {}, targetClass = {}",method.getName(),targetClass.getName());
            return method.getName().equals(matchName);
        }

        @Override
        public boolean isRuntime() {
            //정적인 정보를 사용하기때문에, 캐싱이 적용되어 성능향상 가능
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
