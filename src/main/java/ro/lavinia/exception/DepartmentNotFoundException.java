package ro.lavinia.exception;

public class DepartmentNotFoundException  extends RuntimeException{
    public DepartmentNotFoundException(final String message) {
        super(message);
    }
}
