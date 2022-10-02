package jpabook.jpashop.model;

public enum OrderStatus {
    ORDER(100, "ORDER")
    , CANCEL(101, "CANCEL");

    OrderStatus(int code, String status) {
    }
}
