package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject subject;
    private String cacheData;

    public CacheProxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String operation() {
        log.info("프록시호출");
        if(cacheData == null){
            cacheData = subject.operation();
        }
        return cacheData;
    }
}
