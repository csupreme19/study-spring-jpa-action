package jpabook.jpashop.domain;

import jpabook.jpashop.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .name("memberA")
                .build();
        em.persist(member);

        for (int i = 0; i <3; i++) {
            Order order = Order.builder()
                    .orderDate(LocalDateTime.now())
                    .status(i%2 ==0 ? OrderStatus.ORDER : OrderStatus.CANCEL)
                    .build();
            order.changeMember(member);
            em.persist(order);
        }

        em.flush();
        em.clear();
    }

    @Test
    public void 페치조인_별칭() throws Exception {
        List<Member> members = em.createQuery("select m from Member m"
                                + " left join fetch m.orders o"
                        , Member.class)
                .getResultList();

        members.get(0).getOrders().forEach(o -> System.out.println("Order: " + o.getId()));

        assertThat(members.size()).isNotZero();
    }

    @Test
    public void 페치조인_별칭_일관성이슈() throws Exception {
        List<Member> members = em.createQuery("select m from Member m " +
                                "left join fetch m.orders o " +
                                "where o.status = :status"
                        , Member.class)
                .setParameter("status", OrderStatus.CANCEL)
                .getResultList();

        members.get(0).getOrders().forEach(o -> System.out.println("Order: " + o.getStatus()));

        assertThat(members.get(0).getOrders()).extracting("status").containsExactly(OrderStatus.CANCEL);
    }

}