package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTraceProcessor implements BeanPostProcessor {
    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTraceProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("bean = {}, name = {}", bean.getClass(), beanName);

        //적용 대상이 아니면 그대로 리턴
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)){
            return bean;
        }

        //프록시 대상이면 적용
        ProxyFactory factory = new ProxyFactory(bean);
        factory.addAdvisor(advisor);
        Object proxy = factory.getProxy();
        log.info("create bean = {}, target = {} ", proxy.getClass(),bean.getClass());
        return proxy;
    }
}
