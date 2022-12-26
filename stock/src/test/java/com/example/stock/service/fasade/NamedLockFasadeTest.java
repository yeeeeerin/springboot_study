package com.example.stock.service.fasade;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
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
* 네임드락은 주로 분산락을 구현할때 사용함
* 비관적락은 타임아웃을 구현하기 힘든데 네임드락은 괜찮다.
* 데이터 정합성 맞추기도 괜찮음
* 하지만 락해제와 트렌젝션 세션관리를 잘해줘야하는 단점이 있음
* */
@SpringBootTest
class NamedLockFasadeTest {
    @Autowired
    private NamedLockFasade stockService;
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