package ua.jxlea.exception;

public class ConstraintParseException extends RuntimeException {

    public ConstraintParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintParseException(String message) {
        super(message);
    }
}
