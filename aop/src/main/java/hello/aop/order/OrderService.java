package hello.aop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String orderItem(String itemId) {
        orderRepository.save(itemId);
        return itemId;
    }
}

