package hello.proxy.config.v4_postprocessor;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.postprocessor.PackageLogTraceProcessor;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    /*
    * 컴포넌트 스캔 대상인 bean들까지 로그 trace적용이 가능해졌다
    * 하지만 아직 어떤 컴포넌트에만 로그를 적용해야할지 기준을 정하는 부분이 아쉽다
    * ++스프링빈중에 프록시객체로 생성할 수 없는 빈들이 있기때문에 모든 객체를 프록시로 만들면 문제가 생길 수 있다.
    * */

    @Bean
    public PackageLogTraceProcessor processor(LogTrace logTrace){
        return new PackageLogTraceProcessor("hello.proxy.app",getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*","order*","save*");
        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }

}
