package ro.lavinia.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final String message) {
        super(message);
    }
}
