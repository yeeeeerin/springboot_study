package com.example.stock.service.fasade;

import com.example.stock.domain.Stock;
import com.example.stock.service.OptomisticLockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* 낙관적락에서는 실패할경우를 처리하기위해 facade를 구현해줘야한다!
* */
@Service
public class OptimisticLockStockFacade {
    private final OptomisticLockService service;

    public OptimisticLockStockFacade(OptomisticLockService service) {
        this.service = service;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true){
            try {
                service.decrease(id,quantity);
                break;
            }catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
