package jpabook.jpashop.exception;

public class OrderCancelException extends RuntimeException {

    private static final String MESSAGE = "주문 취소 오류";

    public OrderCancelException() {
        super(MESSAGE);
    }

    public OrderCancelException(String message) {
        super(message);
    }

    public OrderCancelException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderCancelException(Throwable cause) {
        super(cause);
    }

    protected OrderCancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
