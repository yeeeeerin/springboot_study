package hello.proxy.app.v3;

import org.springframework.stereotype.Repository;

@Repository //자동 컴포넌트 검색 대상임
public class OrderRepositoryV3 {
    public void save(String itemId) {
        if(itemId.equals("ex")){
            throw new IllegalArgumentException("예외발생!");
        }
        sleep(1000);
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
