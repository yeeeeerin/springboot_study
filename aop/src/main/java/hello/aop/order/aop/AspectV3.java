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
    * 만약 어드바이스 순서를 바꾸고 싶으면 어떡할까??
    * doLog -> doTransaction 이아닌 doTransaction -> doLog 한다면?
    * */

    @Around("hello.aop.order.aop.PointCuts.allOrder()") // 포인트컷
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{ //어드바이스
        log.info("[log] {}",joinPoint.getSignature()); // joinPoint.getSignature() -> 메서드의 정보
        return joinPoint.proceed();
    }

    //hello.aop.order 하위 패키지면서 && 클래스이름 패턴이 *Service ////*Serv* 이런것도 가능함
    @Around("hello.aop.order.aop.PointCuts.allOrder() && hello.aop.order.aop.PointCuts.allService()") // 포인트컷
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
