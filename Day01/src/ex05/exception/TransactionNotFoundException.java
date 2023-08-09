package exception;

public class TransactionNotFoundException extends TransactionException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
