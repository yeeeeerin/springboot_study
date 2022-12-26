package com.example.stock.service.fasade;

import com.example.stock.repository.RedisRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettucelLockStockFacade {
    private final StockService stockService;
    private final RedisRepository repository;

    public LettucelLockStockFacade(StockService stockService, RedisRepository repository) {
        this.stockService = stockService;
        this.repository = repository;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (!repository.lock(id)){
            Thread.sleep(100);
        }

        try {
            stockService.decrease(id,quantity);
        }finally {
            repository.unlock(id);
        }
    }
}
