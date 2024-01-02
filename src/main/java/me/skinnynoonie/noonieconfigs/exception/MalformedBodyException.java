package me.skinnynoonie.noonieconfigs.exception;

public class MalformedBodyException extends RuntimeException {

    public MalformedBodyException(String message) {
        super(message);
    }

    public MalformedBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedBodyException(Throwable cause) {
        super(cause);
    }

}
