package com.example.rxjava.ex1;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RxClasses {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    void single(){
        /*
        * Single 로 현시간 통지
        * Single은 데이터를 통지할 때 SingleOnSubscribe를 구현 객체를 전달받음
        * subscribe 시 onNext, onComplete 대신 onSuccess를 사용함
        * */
        Single<String> single = Single.create(emitter -> emitter.onSuccess(DateUtil.now().toString()));
        single.subscribe(
                data -> logger.info("#날짜 = {}",data),
                error -> logger.error(error.toString())
        );
    }

    @Test
    void singleCompect(){
        /*
        * Single 간단버전
        * */
        Single.just(DateUtil.now().toString())
                .subscribe(
                        data -> logger.info("#날짜 = {}",data),
                        error -> logger.error(error.toString())
                );
    }

    @Test
    void maybe(){
        /*
        * Maybe클래스를 통해 데이터를 통지
        * 데이터를 통지할 때 MaybeOnSubscribe를 구현객체로 전달받음
        * */
        Maybe.just(DateUtil.now().toString())
                .subscribe(
                        data -> logger.info("#날짜 = {}",data),
                        (error -> logger.error(error.toString())),
                        ()-> logger.info("종료!")
                );

        Maybe.empty()
                .subscribe(
                        data -> logger.info("#날짜 = {}",data),
                        (error -> logger.error(error.toString())),
                        ()-> logger.info("종료!")
                );
    }

    @Test
    void maybeFromSingle(){
        /*
        * Single객체를 Maybe로 변환 후 작업
        * */
        Single<String> single = Single.create(emitter -> emitter.onSuccess(DateUtil.now().toString()));
        Maybe.fromSingle(single)
                .subscribe(
                        data -> logger.info("#날짜 = {}",data),
                        (error -> logger.error(error.toString())),
                        ()-> logger.info("종료!")
                );
    }

    @Test
    void completable(){
        /*
        * Completable은 완료만 통지하고있기때믄 onSuccess 없음
        * */
        Completable completable = Completable.create(emitter -> {
            for (int i = 0; i < 10; i++) {
                logger.info("i={}", i);
            }
            emitter.onComplete();
        });
        completable.subscribeOn(Schedulers.computation()) //subscribeOn 생산자에서 데이터 통지하는 스레드를 따로둘 수 있음
                .subscribe(
                ()-> logger.info("종료!"),
                error -> logger.error(error.toString())
        );
    }
}
