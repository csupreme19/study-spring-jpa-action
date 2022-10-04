package jpabook.jpashop.exception;

public class MemberDuplicateException extends RuntimeException {

    private static final String DEFAULT_MSG = "이미 존재하는 회원입니다.";

    public MemberDuplicateException() {
        super(DEFAULT_MSG);
    }

}
