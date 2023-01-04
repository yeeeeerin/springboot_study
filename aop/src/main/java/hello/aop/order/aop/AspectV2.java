package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class AspectV2 {

    /*
    * 포인트컷 분리
    * 장점 : 의미부여, 포인트컷 모아두고 다른곳에서 참조하는식으로 모듈화 가능
    * */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //Pointcut Signature

    @Around("allOrder()") // 포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ //어드바이스
        log.info("[log] {}",joinPoint.getSignature()); // joinPoint.getSignature() -> 메서드의 정보
        return joinPoint.proceed();
    }
}
