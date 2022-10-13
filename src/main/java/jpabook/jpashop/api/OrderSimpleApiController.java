package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.model.OrderSearch;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> list = orderRepository.findOrdersQuerydsl(new OrderSearch());
        for(Order order : list) {
            order.getMember().getName();    // 지연 로딩 활성화
            order.getDelivery().getAddress();   // 지연 로딩 활성화
        }
        return list;
    }

}
