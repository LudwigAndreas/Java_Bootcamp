
import exception.TransactionNotFoundException;

import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction transaction) throws NullPointerException;
    void deleteTransaction(UUID id) throws TransactionNotFoundException;
    Transaction getTransaction(UUID id) throws TransactionNotFoundException;
    Transaction[] toArray();
}
