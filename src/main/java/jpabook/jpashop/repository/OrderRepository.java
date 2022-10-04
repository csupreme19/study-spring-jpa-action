package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        if(order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
        return order.getId();
    }

    public List<Order> findOrders() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }
}
