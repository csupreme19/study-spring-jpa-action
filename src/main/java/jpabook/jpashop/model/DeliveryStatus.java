package jpabook.jpashop.model;

public enum DeliveryStatus {
    READY(200, "READY")
    , DELIVERED(201, "DELIVERED");

    DeliveryStatus(int code, String message) {
    }
}
