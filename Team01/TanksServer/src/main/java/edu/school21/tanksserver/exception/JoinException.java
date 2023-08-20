package edu.school21.tanksserver.exception;

public class JoinException extends ServerException {
    public JoinException() {
    }

    public JoinException(String message) {
        super(message);
    }

    public JoinException(String message, Throwable cause) {
        super(message, cause);
    }
}
