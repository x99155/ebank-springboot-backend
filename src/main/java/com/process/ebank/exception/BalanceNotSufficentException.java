package com.process.ebank.exception;

public class BalanceNotSufficentException extends RuntimeException {

    public BalanceNotSufficentException(String message) {
        super(message);
    }
}
