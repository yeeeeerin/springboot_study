package com.example.stock.service.fasade;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import com.example.stock.service.OptomisticLockService;
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
* 낙관적락
* 장점 - 디비 락을 잡고있는동안 기다리는게 아니라 성능상 좋을 수 있음
* 단접
* - facade, 실패할 경우 로직을 작성해줘야함
* */
@SpringBootTest
class OptimisticLockStockFacadeTest {

    @Autowired
    private OptimisticLockStockFacade stockService;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void before(){
        Stock stock = new Stock(1L,100L);
        stockRepository.save(stock);
    }

    @AfterEach
    void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void stock_decrease() throws InterruptedException {
        stockService.decrease(1L,1L);
        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertEquals(99L,stock.getQuantity());
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
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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