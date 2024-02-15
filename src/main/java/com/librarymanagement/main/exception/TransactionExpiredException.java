package com.librarymanagement.main.exception;

public class TransactionExpiredException extends RuntimeException {
    public TransactionExpiredException(String message) {
        super(message);
    }
}
