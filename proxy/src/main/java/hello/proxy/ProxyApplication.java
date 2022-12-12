package hello.proxy;

import hello.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV1Config.class) //따로 임포트를 한 이유? -> 버전별로 config파일 만들어서 관리하려곤
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //지정한 위치 하위 파일들만 컴포넌트 스캔을한다.
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
