package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.StockNotEnoughException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Inheritance
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /*
    비즈니스 로직
     */

    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소
    public void minusStock(int quantity) {
        if(this.stockQuantity - quantity < 0) throw new StockNotEnoughException();
        this.stockQuantity -= quantity;
    }
}
