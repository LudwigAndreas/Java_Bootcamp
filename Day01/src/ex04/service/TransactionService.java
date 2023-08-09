package service;

import exception.IllegalTransactionException;
import model.Transaction.Transaction;
import model.Transaction.TransactionsLinkedList;
import model.Transaction.TransactionsList;
import model.User.User;
import model.User.UsersArrayList;
import model.User.UsersList;

public class TransactionService {
    private UsersList usersList = new UsersArrayList();

    public UsersList getUsersList() {
        return usersList;
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(User user) {
        return user.getBalance();
    }

    public void transferMoney(User fromUser, User toUser, int amount) throws IllegalTransactionException {
        if (fromUser.getBalance() < amount) {
            throw new IllegalTransactionException("Not enough money to transfer from " + fromUser.getName() + " to " + toUser.getName() + "");
        }
        Transaction debit = new Transaction(toUser, fromUser, amount);
        Transaction credit = Transaction.inverse(debit);
        fromUser.addTransaction(credit);
        toUser.addTransaction(debit);
        fromUser.subtractBalance(amount);
        toUser.addBalance(amount);
    }

    public TransactionsList getUserTransactions(User user) {
        return user.getTransactionsList();
    }

    public Transaction[] getValidTransactions() {
        TransactionsList tl = new TransactionsLinkedList();
        for (User user : usersList.toArray()) {
            for (Transaction transaction : user.getTransactionsList().toArray()) {
                if (transaction.isValid()) {
                    tl.addTransaction(transaction);
                }
            }
        }
        return tl.toArray();
    }
}
