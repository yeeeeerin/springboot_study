package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/*
* @Around와
* */

@Aspect
@Slf4j
public class AspectV6Advice {
/*    @Around("hello.aop.order.aop.PointCuts.allOrder() && hello.aop.order.aop.PointCuts.allService()") // 포인트컷
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{ //어드바이스
        try {
            //@Before
            log.info("트랜젝션 시 {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //AfterReturning
            log.info("트랜젝션 시 {}", joinPoint.getSignature());
            return result;
        }catch (Exception e){
            //AfterThrowing
            log.info("롤백 {}", joinPoint.getSignature());
            throw e;
        }finally {
            //After
            log.info("리소스 릴리즈 {}", joinPoint.getSignature());
        }
    }*/

    @Before("hello.aop.order.aop.PointCuts.allOrder()")
    public void doBefore(JoinPoint joinPoint){ //파라메터는 필수가 아님
        log.info("트랜젝션 시 {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.PointCuts.allOrder()", returning = "result")
    public void afterReturning(Object result){
        log.info("리턴 시 {}", result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.PointCuts.allOrder()", throwing = "th")
    public void afterThrow(Exception th){
        log.info("exception = {}",th.getMessage());
    }

    @After("hello.aop.order.aop.PointCuts.allOrder()")
    public void after(JoinPoint joinPoint){
        log.info("리소스 릴리즈 {}", joinPoint.getSignature());
    }
}
