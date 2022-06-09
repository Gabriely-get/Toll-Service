package org.gabrielyget.cloudnative.Tema05.CustomExceptions;

public class InsufficientMoneyException extends RuntimeException {
    public InsufficientMoneyException() {

    }

    public InsufficientMoneyException(String errorMessage) {
        super(errorMessage);
    }

    public InsufficientMoneyException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }
}
