package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.model.OrderSearch;
import jpabook.jpashop.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static jpabook.jpashop.domain.QMember.member;
import static jpabook.jpashop.domain.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    private final JPAQuery<Order> jpaQuery;

    public Long save(Order order) {
        if(order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
        return order.getId();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o " +
                        "join o.member m " +
                        "where m.name like :name and o.status = :status", Order.class)
                .setParameter("name", "%" + orderSearch.getMemberName() + "%")
                .setParameter("status", orderSearch.getOrderStatus())
                .setFirstResult(0)
                .setMaxResults(1000)
                .getResultList();
    }

    public List<Order> findOrdersFetchJoin(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o " +
                        "join fetch o.member m " +
                        "join fetch o.delivery d", Order.class)
                .getResultList();
    }

    public List<Order> findOrdersJPQL(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if(orderSearch.getOrderStatus() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        if(hasText(orderSearch.getMemberName())) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if(orderSearch.getOrderStatus() != null) query = query.setParameter("status", orderSearch.getOrderStatus());
        if(hasText(orderSearch.getMemberName())) query = query.setParameter("name", orderSearch.getMemberName());

        return query.getResultList();
    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        if(orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        if(hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findOrdersQuerydsl(OrderSearch orderSearch) {
        return jpaQuery.select(order)
                .from(order)
                .join(order.member, member).fetchJoin()
                .where(statusEq(orderSearch)
                        , nameContains(orderSearch))
                .limit(1000)
                .fetch();
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    private BooleanExpression statusEq(OrderSearch orderSearch) {
        OrderStatus status = orderSearch.getOrderStatus();
        return status != null ? order.status.eq(status) : null;
    }

    private BooleanExpression nameContains(OrderSearch orderSearch) {
        String name = orderSearch.getMemberName();
        return hasText(name) ? member.name.contains(name) : null;
    }
}
