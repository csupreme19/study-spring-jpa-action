package jpabook.jpashop.domain.item;

import jpabook.jpashop.exception.StockNotEnoughException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void addStock() throws Exception {
        // given
        int stockQuantity = 10;
        int price = 1000;
        Movie movie = Movie.builder()
                .name("album")
                .stockQuantity(stockQuantity)
                .price(price)
                .build();
        int quantity = 5;

        // when
        movie.addStock(quantity);

        // then
        assertThat(movie.getStockQuantity()).as("상품 재고 수량이 정확해야한다.").isEqualTo(stockQuantity + quantity);
    }

    @Test
    void minusStock() {
        // given
        int stockQuantity = 10;
        int price = 1000;
        Movie movie = Movie.builder()
                .name("album")
                .stockQuantity(stockQuantity)
                .price(price)
                .build();
        int quantity = 5;

        // when
        movie.minusStock(quantity);

        // then
        assertThat(movie.getStockQuantity()).as("상품 재고 수량이 정확해야한다.").isEqualTo(stockQuantity - quantity);
    }

    @Test
    public void minusStock_예외() throws Exception {
        // given
        int stockQuantity = 10;
        int price = 1000;
        Movie movie = Movie.builder()
                .name("album")
                .stockQuantity(stockQuantity)
                .price(price)
                .build();
        int quantity = 15;

        // then
        assertThrows(StockNotEnoughException.class, () -> movie.minusStock(quantity), "상품 재고 부족 예외가 발생해야한다.");
    }
}