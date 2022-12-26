package com.example.stock.service.fasade;

import com.example.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedissonFacade {
    private RedissonClient client;
    private StockService stockService;

    public RedissonFacade(RedissonClient client, StockService stockService) {
        this.client = client;
        this.stockService = stockService;
    }

    public void decrease(Long key,Long quantity){
        RLock rLock = client.getLock(key.toString());
        try {
            //몇초동안 몇번 점유할건지 락획득 시작
            boolean tryLock = rLock.tryLock(5, 1, TimeUnit.SECONDS);
            if(!tryLock){
                log.info("lock 획득 실패");
                return;
            }
            stockService.decrease(key,quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            rLock.unlock();
        }
    }
}
