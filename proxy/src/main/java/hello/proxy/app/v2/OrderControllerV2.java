package hello.proxy.app.v2;


import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/*
수동 컴포넌트 등록을 위해서 @RequestMapping, @ResponseBody 사용
RestContoller 는 @Component 어노테이션이 포함되어있어 자동 컴포넌트 스캔의 대상이된다
 */
@RequestMapping
@ResponseBody
@Slf4j
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderServiceV2) {
        this.orderService = orderServiceV2;
    }

    @GetMapping("/v2/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    public String noLog() {
        return null;
    }
}
