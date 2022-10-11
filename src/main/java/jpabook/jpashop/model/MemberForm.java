package jpabook.jpashop.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "값이 존재해야 합니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
