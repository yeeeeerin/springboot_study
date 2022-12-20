package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class MultiAdvisorTest {
    @Test
    @DisplayName("여러프록시")
    void multiadvisor1(){
        //프록시 1 생성
        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory1 = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        factory1.addAdvisor(advisor1);
        ServiceInterface prox1 = (ServiceInterface) factory1.getProxy();

        //프록시 2 생성
        ProxyFactory factory2 = new ProxyFactory(prox1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        factory2.addAdvisor(advisor2);
        ServiceInterface prox2 = (ServiceInterface) factory2.getProxy();

        prox2.save();
        prox2.find();
    }

    @Test
    @DisplayName("하나의 프록시")
    void multiadvisor2(){
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        ServiceInterface target = new ServiceImpl();
        ProxyFactory factory = new ProxyFactory(target);
        factory.addAdvisor(advisor2);
        factory.addAdvisor(advisor1);
        ServiceInterface prox = (ServiceInterface) factory.getProxy();

        prox.save();
        prox.find();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice1 호출");
            return invocation.proceed();
        }
    }
    @Slf4j
    static class Advice2 implements MethodInterceptor{
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice2 호출");
            return invocation.proceed();
        }
    }

}
