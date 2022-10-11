package jpabook.jpashop.domain;

import jpabook.jpashop.exception.OrderCancelException;
import jpabook.jpashop.model.DeliveryStatus;
import jpabook.jpashop.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(STRING)
    private OrderStatus status;

    public void changeMember(Member member) {
        member.getOrders().add(this);
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.changeOrder(this);
        this.orderItems.add(orderItem);
    }

    public void changeDelivery(Delivery delivery) {
        delivery.changeOrder(this);
        this.delivery = delivery;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    /*
    비즈니스 로직
     */

    // 주문 취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.DELIVERED) {
            throw new OrderCancelException("배송완료된 상품은 취소할 수 없습니다.");
        }
        changeStatus(OrderStatus.CANCEL);
        orderItems.forEach(OrderItem::cancel);
    }

    /*
    조회 로직
     */

    // 전체 주문 가격 조회
    public int getTotalPrice() {
        return getOrderItems().stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
