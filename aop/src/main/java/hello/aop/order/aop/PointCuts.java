package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/*
 * 포인트컷 분리
 * 장점 : 의미부여, 포인트컷 모아두고 다른곳에서 참조하는식으로 모듈화 가능
 * */
public class PointCuts {
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} //Pointcut Signature

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){};
}
