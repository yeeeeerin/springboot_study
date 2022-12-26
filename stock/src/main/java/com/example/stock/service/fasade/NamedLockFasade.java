package com.example.stock.service.fasade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class NamedLockFasade {
    private final LockRepository repository;
    private final StockService service;

    public NamedLockFasade(LockRepository repository, StockService stockService) {
        this.repository = repository;
        this.service = stockService;
    }

    public void decrease(Long id, Long quantity) {
        try {
            repository.getLock(id.toString());
            service.decrease(id,quantity);
        }finally {
            repository.releaseLock(id.toString());
        }
    }
}
