package jpabook.jpashop.exception;

public class StockNotEnoughException extends RuntimeException {

    private static final String MESSAGE = "재고 수량이 부족합니다.";

    public StockNotEnoughException() {
        super(MESSAGE);
    }

    public StockNotEnoughException(String message) {
        super(message);
    }

    public StockNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected StockNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
