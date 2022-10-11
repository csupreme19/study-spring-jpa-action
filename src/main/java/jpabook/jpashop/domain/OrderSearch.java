package jpabook.jpashop.domain;

import jpabook.jpashop.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
