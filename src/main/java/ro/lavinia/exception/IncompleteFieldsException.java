package ro.lavinia.exception;

public class IncompleteFieldsException extends RuntimeException{
    public IncompleteFieldsException(final String message) {
        super(message);
    }
}
