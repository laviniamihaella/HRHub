package ro.lavinia.exception;

public class NotEnoughAttributeException extends RuntimeException{
    public NotEnoughAttributeException(final String message) {
        super(message);
    }
}
