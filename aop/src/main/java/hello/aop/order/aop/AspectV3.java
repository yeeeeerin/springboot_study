package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class AspectV3 {

    /*
    * 포인트컷 분리
    * 장점 : 의미부여, 포인트컷 모아두고 다른곳에서 참조하는식으로 모듈화 가능
    * */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //Pointcut Signature

    @Pointcut("execution(* *..*Service.*..*(..))")
    private void allService(){}

    @Around("allOrder()") // 포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ //어드바이스
        log.info("[log] {}",joinPoint.getSignature()); // joinPoint.getSignature() -> 메서드의 정보
        return joinPoint.proceed();
    }

    //hello.aop.order 하위 패키지면서 && 클래스이름 패턴이 *Service ////*Serv* 이런것도 가능함
    @Around("allOrder() && allService()") // 포인트컷
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{ //어드바이스
        try {
            log.info("트랜젝션 시 {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("트랜젝션 시 {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            log.info("롤백 {}", joinPoint.getSignature());
            throw e;
        }finally {
            log.info("리소스 릴리즈 {}", joinPoint.getSignature());
        }
    }
}
