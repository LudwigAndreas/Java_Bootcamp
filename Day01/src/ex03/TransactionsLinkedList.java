import exception.TransactionNotFoundException;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Node head = new Node(null, null, null);
    private final Node tail = new Node(null, null, null);
    private int size = 0;

    private class Node {
        private Transaction transaction;
        private Node next = null;
        private Node previous = null;

        public Node(Transaction transaction) {
            this.transaction = transaction;
        }

        public Node(Transaction transaction, Node next, Node previous) {
            this.transaction = transaction;
            this.next = next;
            this.previous = previous;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }

    public TransactionsLinkedList() {
        head.setNext(tail);
        tail.setPrevious(head);
    }

    public int size() {
        return size;
    }

    @Override
    public void addTransaction(Transaction transaction) throws NullPointerException {
        Node next = head.getNext();
        Node newNode = new Node(transaction, next, head);
        head.setNext(newNode);
        next.setPrevious(newNode);
        size++;
    }

    @Override
    public void deleteTransaction(UUID id) throws TransactionNotFoundException {
        Node current = head.getNext();
        while (current != tail) {
            if (current.getTransaction().getId().equals(id)) {
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                size--;
                return;
            }
            current = current.getNext();
        }
        throw new TransactionNotFoundException("Transaction with id " + id + " not found");
    }

    @Override
    public Transaction getTransaction(UUID id) throws TransactionNotFoundException {
        Node current = head.getNext();
        while (current != tail) {
            if (current.getTransaction().getId().equals(id)) {
                return current.getTransaction();
            }
            current = current.getNext();
        }
        throw new TransactionNotFoundException("Transaction with id " + id + " not found");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node current = head.getNext();
        for (int i = 0; i < size; i++) {
            transactions[i] = current.getTransaction();
            current = current.getNext();
        }
        return transactions;
    }
}
