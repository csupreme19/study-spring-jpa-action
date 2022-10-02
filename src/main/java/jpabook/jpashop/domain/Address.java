package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
