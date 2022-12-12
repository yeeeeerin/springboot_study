package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceV1;

public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;

    public OrderServiceV2(OrderRepositoryV2 orderRepositoryV1) {
        this.orderRepository = orderRepositoryV1;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
