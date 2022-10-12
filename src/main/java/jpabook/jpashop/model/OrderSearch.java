package jpabook.jpashop.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
