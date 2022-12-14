package hello.proxy.pureproxy.concreterpoxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogic {
    public String operation(){
        log.info("ConcreteLogic execute");
        return "data";
    }
}
