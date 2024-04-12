package ro.lavinia.exception;

public class FieldNotFoundException extends RuntimeException {
  public FieldNotFoundException(final String message) {
    super(message);
  }
}
