package jpabook.jpashop.model;

public enum AccessType {
    CREATE("생성")
    , READ("조회")
    , UPDATE("수정")
    , DELETE("삭제");

    AccessType(String name) {
    }
}
