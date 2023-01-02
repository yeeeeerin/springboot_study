package com.example.rxjava;

import io.reactivex.Observable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RxJavaApplication {

    public static void main(String[] args) {
        Observable<String> observable = Observable.just("hello","RxJava");
        observable.subscribe(System.out::println);
        SpringApplication.run(RxJavaApplication.class, args);

    }

}
