package jpabook.jpashop.exception;

public class MemberDuplicateException extends RuntimeException {

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public MemberDuplicateException() {
        super(MESSAGE);
    }

    public MemberDuplicateException(String message) {
        super(message);
    }

    public MemberDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberDuplicateException(Throwable cause) {
        super(cause);
    }

    protected MemberDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
