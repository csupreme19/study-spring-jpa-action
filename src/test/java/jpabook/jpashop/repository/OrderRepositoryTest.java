package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        for(int i=0; i<10; i++) {
            Member member = Member.builder()
                    .name("member" + i)
                    .build();
            em.persist(member);

            Order order = Order.builder()
                    .member(member)
                    .status(i % 2 == 0 ? OrderStatus.ORDER : OrderStatus.CANCEL)
                    .build();
            em.persist(order);
        }

        em.flush();
        em.clear();
    }

    @Test
    public void findOrdersJPQL() throws Exception {
        // given
        OrderSearch orderSearch = OrderSearch.builder()
                .orderStatus(OrderStatus.ORDER)
                .build();

        // when
        List<Order> orders = orderRepository.findOrdersJPQL(orderSearch);

        // then
        assertThat(orders).isNotNull();
        assertThat(orders).extracting("status").contains(OrderStatus.ORDER);
    }

    @Test
    public void findOrdersQuerydsl() throws Exception {
        // given
        OrderSearch orderSearch = OrderSearch.builder()
                .orderStatus(OrderStatus.ORDER)
                .build();

        // when
        List<Order> orders = orderRepository.findOrdersQuerydsl(orderSearch);

        // then
        assertThat(orders).isNotNull();
        assertThat(orders).extracting("status").contains(OrderStatus.ORDER);
    }

}