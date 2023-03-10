package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item"
    , joinColumns = @JoinColumn(name = "category_id")
    , inverseJoinColumns = @JoinColumn(name = "item_id"))
    @Builder.Default
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @Builder.Default
    private List<Category> child = new ArrayList<>();

    public void changeParent(Category parent) {
        this.parent = parent;
    }

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.changeParent(this);
    }

}
