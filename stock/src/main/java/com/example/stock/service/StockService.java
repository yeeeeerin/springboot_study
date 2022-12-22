package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //@Transactional
    public synchronized void decrease(Long id,Long quantity){
        //stock 꺼내기
        //재고 감소하기
        //저장

        Stock stock = stockRepository.findById(id).get();
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}

/*
* Transactional
* Transactional 종료시점에 데이터를 update하기때문에
* decrease가 완료하고 업데이트 하기 직전에 decrease에 접근할 수 있다
*
* 방법1) Transactional주석 후 synchronized만 두고 쓰기
*
* synchronized의 문제점
* 각 프로세스 안에서만 보장되기때문에 서버가 2대 이상일 경우 문제가될 수 있다.
* */
