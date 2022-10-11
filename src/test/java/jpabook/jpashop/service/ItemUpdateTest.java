package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        // given
        Book book = Book.builder()
                .name("test")
                .price(1000)
                .stockQuantity(10)
                .build();
        em.persist(book);

        em.flush();
        em.clear();

        Book findBook = em.find(Book.class, 1L);

        String name = "modified";

        // when
        findBook.setName(name);   // dirty checking

        // then
        assertThat(findBook.getName()).as("변경 감지로 이름이 변경되어야한다.").isEqualTo(name);
    }

}
