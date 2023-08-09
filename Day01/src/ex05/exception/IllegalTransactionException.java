package exception;

public class IllegalTransactionException extends Throwable {
    public IllegalTransactionException(String transaction_is_not_valid) {
        super(transaction_is_not_valid);
    }
}
