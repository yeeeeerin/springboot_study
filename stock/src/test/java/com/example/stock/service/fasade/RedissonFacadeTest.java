package com.example.stock.service.fasade;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/*
* 장점 : 간단함, 부하가 적음(pup sub 구조라)
* 단점 : 별도 라이브러리 추가해야함
* */
@SpringBootTest
class RedissonFacadeTest {
    @Autowired
    private RedissonFacade stockService;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void before(){
        Stock stock = new Stock(1L,100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void 동시에_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        //비동기로 실행해야하는 작업을 단순화하여 사용할 수 있도록 도와주는 자바의 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //100개의 작업이 끝날때까지 기다리기 위해서 CountDownLatch 사용, 다른 thread에서 수행중인 작업이 끝날때까지 대기함
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i=0;i<threadCount;i++){
            executorService.submit(()->{
                try {
                    stockService.decrease(1L,1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertEquals(0L,stock.getQuantity());
    }
}