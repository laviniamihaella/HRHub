package ro.lavinia.exception;

public class SaveErrorException  extends RuntimeException{
    public SaveErrorException(final String message) {
        super(message);
    }

}
