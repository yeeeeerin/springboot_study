package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("Time proxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = methodProxy.invoke(target, args);
        //methodProxy의 invoke를 쓰는게 method invoke 보다 빠름

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("Time proxy 종료 resultTime == {}",resultTime);
        return result;
    }
}
