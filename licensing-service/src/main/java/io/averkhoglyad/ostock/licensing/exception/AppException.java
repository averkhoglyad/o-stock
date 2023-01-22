package io.averkhoglyad.ostock.licensing.exception;

import io.averkhoglyad.ostock.licensing.data.Message;

public class AppException extends RuntimeException {

    private final Message message;

    public AppException(String message, Object... args) {
        this.message = new Message(message, args);
    }

    public AppException(Message message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public Message message() {
        return message;
    }
}
