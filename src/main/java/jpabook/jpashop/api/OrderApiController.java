package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.model.OrderDto;
import jpabook.jpashop.model.OrderFlatDto;
import jpabook.jpashop.model.OrderQueryDto;
import jpabook.jpashop.model.OrderSearch;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderQueryService orderQueryService;

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> list = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : list) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }
        return list;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> list = orderRepository.findAllByCriteria(new OrderSearch());
        return list.stream().map(OrderDto::new).toList();
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return orderQueryService.ordersV3();
//        List<Order> list = orderRepository.findAllWithItem(new OrderSearch());
//        return list.stream().map(OrderDto::new).toList();
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset
            , @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> list = orderRepository.findAllWithMemberDelivery(offset, limit);
        return list.stream().map(OrderDto::new).toList();
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6() {
        return orderQueryRepository.findAllByDto_flat();
    }

}
