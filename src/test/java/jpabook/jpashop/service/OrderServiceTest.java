package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.StockNotEnoughException;
import jpabook.jpashop.model.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(Address.builder()
                        .city("서울")
                        .street("강남대로")
                        .zipcode("123-456")
                        .build())
                .build();
        em.persist(member);

        int bookPrice = 10000;
        int bookStockQuantity = 10;
        Book book = Book.builder()
                .name("책1")
                .price(bookPrice)
                .stockQuantity(bookStockQuantity)
                .build();
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order order = orderRepository.findById(orderId);
        assertThat(order.getStatus()).as("상품 주문시 상태는 ORDER이다.").isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).as("주문한 상품 종류 수가 정확하다.").isEqualTo(1);
        assertThat(order.getTotalPrice()).as("주문상품 가격은 가격 * 수량이다.").isEqualTo(bookPrice * orderCount);
        assertThat(book.getStockQuantity()).as("주문 수량만큼 재고가 줄어야한다.").isEqualTo(bookStockQuantity - orderCount);
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(Address.builder()
                        .city("서울")
                        .street("강남대로")
                        .zipcode("12345")
                        .build())
                .build();
        em.persist(member);

        int albumStockQuantity = 10;
        int albumPrice = 10000;
        Album album = Album.builder()
                .name("album")
                .stockQuantity(albumStockQuantity)
                .price(albumPrice)
                .build();
        em.persist(album);

        int orderCount = 5;

        Long orderId = orderService.order(member.getId(), album.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findById(orderId);
        assertThat(order.getStatus()).as("상품 주문 취소시 상태는 CANCEL이다.").isEqualTo(OrderStatus.CANCEL);
        assertThat(order.getOrderItems().size()).as("취소한 상품 종류 수가 정확하다.").isEqualTo(1);
        assertThat(order.getTotalPrice()).as("취소 주문상품 가격은 가격 * 수량이다.").isEqualTo(albumPrice * orderCount);
        assertThat(album.getStockQuantity()).as("취소 수량만큼 재고가 늘어야한다.").isEqualTo(albumStockQuantity);
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(Address.builder()
                        .city("서울")
                        .street("강남대로")
                        .zipcode("12345")
                        .build())
                .build();
        em.persist(member);

        Movie movie = Movie.builder()
                .name("album")
                .stockQuantity(10)
                .price(1000)
                .build();
        em.persist(movie);

        int orderCount = 100;

        // then
        assertThrows(StockNotEnoughException.class, () -> orderService.order(member.getId(), movie.getId(), orderCount), "재고가 부족하면 예외가 발생한다.");
    }

    @Test
    public void 주문취소_재고수량초과() throws Exception {
        // given
        Member member = Member.builder()
                .name("회원1")
                .address(Address.builder()
                        .city("서울")
                        .street("강남대로")
                        .zipcode("12345")
                        .build())
                .build();
        em.persist(member);

        Movie movie = Movie.builder()
                .name("album")
                .stockQuantity(10)
                .price(1000)
                .build();
        em.persist(movie);

        int orderCount = 100;

        // then
        assertThrows(StockNotEnoughException.class, () -> orderService.order(member.getId(), movie.getId(), orderCount));
    }
}