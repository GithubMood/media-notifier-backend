package com.media.notifier.common.exception;

public class DbResultNotFoundException extends RuntimeException {
    public DbResultNotFoundException(String message) {
        super(message);
    }
}
