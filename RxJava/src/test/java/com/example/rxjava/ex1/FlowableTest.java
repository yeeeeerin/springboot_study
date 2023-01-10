package com.example.rxjava.ex1;

import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class FlowableTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void flowable() throws InterruptedException {
        /*
        * 실행 결과 :MissingBackpressureException 발생
        * 이유 : 시간이 없어서
        * 봐야할 부분 :
        * 다른스레드에서 처리와 통제를 하고있음
        * [ionThreadPool-1] com.example.rxjava.ex1.FlowableTest      : #소비자 대기
        * [ionThreadPool-2] com.example.rxjava.ex1.FlowableTest      : 0
        * */

        Flowable.interval(1L, TimeUnit.MILLISECONDS) //데이터를 통제하고
                .doOnNext(data -> logger.info("{}",data)) //interval에서 데이터를 통제할 때 실행되는 콜백함수
                .observeOn(Schedulers.computation()) //데이터를 처리하는 스레드 분리
                .subscribe( //데이터를 구독하고
                        data -> { //데이터를 처리한다
                            logger.info("#소비자 대기");
                            Thread.sleep(1000l);
                            logger.info("{}",data);
                        },
                        error -> logger.error("{}",error),
                        ()->logger.info("")
                );
        Thread.sleep(2000l);

    }

    @Test
    public void bufferLatest() throws InterruptedException {
        /*
        * DROP_LATEST 배압전략
        *
        * */
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> logger.info("#doOnNext={}",data))
                .onBackpressureBuffer(
                        2, //버퍼에 들어올 수 있는 개수
                        ()->logger.info("overflow!"),
                        BackpressureOverflowStrategy.DROP_LATEST)
                .doOnNext(data -> logger.info("#onBackpressureBuffer doOnNext={}",data))
                .observeOn(Schedulers.computation(),false,1) //여기서 버퍼사이즈는 소비자쪽 요청 데이터 개수
                .subscribe(
                        data -> {
                            Thread.sleep(1000l);
                            logger.info("데이터 처리",data);
                        },
                        error -> logger.error("{}",error)
                );
        Thread.sleep(4000l);
    }

    @Test
    public void bufferOldest() throws InterruptedException {
        /*
         * DROP_OLDEST 배압전략
         *
         * */
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> logger.info("#doOnNext={}",data)) //interval 에서 어떤 데이터를 통지했는지
                .onBackpressureBuffer(
                        2, //버퍼에 들어올 수 있는 개수
                        ()->logger.info("overflow!"), //버퍼가 가득 찼을 때
                        BackpressureOverflowStrategy.DROP_OLDEST)
                .doOnNext(data -> logger.info("#onBackpressureBuffer doOnNext={}",data)) //버퍼내에서 데이터가 통지 될 때
                .observeOn(Schedulers.computation(),false,1) //여기서 버퍼사이즈는 소비자쪽 요청 데이터 개수
                .subscribe( //소비자 데이터 처리
                        data -> {
                            Thread.sleep(1000l);
                            logger.info("데이터 처리",data);
                        },
                        error -> logger.error("{}",error)
                );
        Thread.sleep(4000l);
    }


    @Test
    public void drop() throws InterruptedException {
        /*
         * DROP 배압전략
         *
         * */
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> logger.info("#doOnNext={}",data)) //interval 에서 어떤 데이터를 통지했는지
                .onBackpressureDrop(data -> logger.info("drop = {}", data))
                .observeOn(Schedulers.computation(),false,1) //여기서 버퍼사이즈는 소비자쪽 요청 데이터 개수
                .subscribe( //소비자 데이터 처리
                        data -> {
                            Thread.sleep(1000l);
                            logger.info("데이터 처리={} ",data);
                        },
                        error -> logger.error("{}",error)
                );
        Thread.sleep(4000l);
    }

    @Test
    public void latest() throws InterruptedException {
        /*
         * 버퍼가 다 차면 통지된 데이터들 기다리고있다가 비퍼가 다 비워졌을 때, 가장 나중에 통지가 된 데이터를 통지함
         *
         * */
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .doOnNext(data -> logger.info("#doOnNext={}",data)) //interval 에서 어떤 데이터를 통지했는지
                .onBackpressureLatest()
                .observeOn(Schedulers.computation(),false,1) //여기서 버퍼사이즈는 소비자쪽 요청 데이터 개수
                .subscribe( //소비자 데이터 처리
                        data -> {
                            Thread.sleep(1000l);
                            logger.info("데이터 처리 = {}",data);
                        },
                        error -> logger.error("{}",error)
                );
        Thread.sleep(4000l);
    }
}
