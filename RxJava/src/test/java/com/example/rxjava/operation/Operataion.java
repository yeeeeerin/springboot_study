package com.example.rxjava.operation;

import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class Operataion {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    void interval() throws InterruptedException {
        Observable.interval(0L,1000L, TimeUnit.MILLISECONDS)
                .map(num -> num+" count")
                .subscribe(data -> logger.info(data));

        Thread.sleep(3000L);
    }

    @Test
    void range() throws InterruptedException {
        Observable.range(0,5).subscribe(data -> logger.info("data : {}",data));
        Thread.sleep(1000L);
    }

    @Test
    void timer() throws InterruptedException {
        logger.info("START");
        Observable.timer(2000,TimeUnit.MILLISECONDS)
                .map(count -> "Do Work!")
                .subscribe(data -> logger.info(data));

        Thread.sleep(3000L);
    }

    @Test
    void defer() throws InterruptedException {
        Observable<LocalTime> operation = Observable.defer(() -> {
            LocalTime currentTime = LocalTime.now();
            return Observable.just(currentTime);
        });

        operation.subscribe(data -> logger.info("time : {}", data));
        Thread.sleep(3000L);
        operation.subscribe(data -> logger.info("time : {}", data));
    }

    @Test
    void fromIterable() throws InterruptedException {
        List<String> national = Arrays.asList("korea", "china", "usa");
        Observable.fromIterable(national)
                .subscribe(data -> logger.info(data));
        Thread.sleep(3000L);
    }

    @Test
    void future() throws Exception{
        logger.info("start");

        //긴 처리 시간 작업
        Future<Double> future = longTimeWork();

        //짧은 작업
        shortwork();

        Observable.fromFuture(future)
                .subscribe(data -> logger.info("작업결과 : {}",data));

        logger.info("종료");
    }

    private CompletableFuture<Double> longTimeWork() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return calculate();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Double calculate() throws InterruptedException {
        logger.info("작업이 긴 시간 작업 중~~~~");
        Thread.sleep(4000L);
        return 10000000000000000.0;
    }

    private void shortwork() throws InterruptedException {
        Thread.sleep(2000L);
        logger.info("짧은거 완료~~~~");
    }


}
