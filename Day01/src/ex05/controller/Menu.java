package controller;

import exception.IllegalTransactionException;
import exception.TransactionNotFoundException;
import exception.UserNotFoundException;
import model.Transaction.Transaction;
import model.Transaction.TransactionsList;
import model.User.User;
import service.TransactionService;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    public enum Profile {
        DEV,
        PROD
    }
    private static final String inputSymbol = "-> ";
    private boolean isDev = false;
    private TransactionService transactionService;

    public Menu(Profile profile, TransactionService transactionService) {
        if (profile == Profile.DEV) {
            isDev = true;
        }
        this.transactionService = transactionService;
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        while (true) {
            int choose = scanInputCode(in);
            if (choose == -1)
                continue;

            handleInputCode(choose, in);

            printSeparator();
        }
    }

    private void handleInputCode(int choose, Scanner in) {
        switch (choose) {
            case 1:
                addUser(in);
                break;
            case 2:
                viewUserBalances(in);
                break;
            case 3:
                performTransfer(in);
                break;
            case 4:
                viewUserTransactions(in);
                break;
            case 5:
                if (isDev) {
                    removeTransfer(in);
                } else {
                    finishExecution();
                }
                break;
            case 6:
                if (isDev) {
                    checkTransferValidity(in);
                } else {
                    printErrorMessage("Invalid input");
                }
                break;
            case 7:
                if (isDev) {
                    finishExecution();
                } else {
                    printErrorMessage("Invalid input");
                }
                break;
            default:
                printErrorMessage("Invalid input");
        }
    }

    private void finishExecution() {
        printOkMessage("Bye!");
        System.exit(0);
    }

    private void checkTransferValidity(Scanner in) {
        try {
            TransactionsList transactionsList = transactionService.getInvalidTransactions();
            System.out.println("Check results:");
            for (Transaction transaction : transactionsList.toArray()) {
                printOkMessage(
                        transaction.getSender().getName() +
                                "(id = " + transaction.getSender().getId() + ") has unacknowledged transfer id = " +
                                transaction.getId() + " from " + transaction.getRecipient().getName() +
                                "(id = " + transaction.getRecipient().getId() + ") " +
                                " for " + transaction.getAmount()
                );
            }
        } catch (Exception e) {
            printErrorMessage("Invalid input");
        }
    }

    private void removeTransfer(Scanner in) {
        System.out.println("Enter a user ID and a transfer ID:");
        printInputSymbol();
        String[] input = in.nextLine().split(" ");
        if (input.length != 2) {
            printErrorMessage("Invalid input");
            return;
        }
        try {
            Transaction transaction = transactionService.removeTransfer(
                    transactionService.getUserById(Integer.parseInt(input[0])),
                    UUID.fromString(input[1])
            );
            printOkMessage(
                    "Transfer To " + transaction.getRecipient().getName() +
                            "(id = " + transaction.getRecipient().getId() + ") " +
                            -transaction.getAmount() + " removed"
            );
        } catch (TransactionNotFoundException e) {
            printErrorMessage("Transaction not found");
        } catch (UserNotFoundException e) {
            printErrorMessage("User not found");
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        }
    }

    private void performTransfer(Scanner in) {
        System.out.println("Enter sender ID, a recipient ID and a transfer amount:");
        printInputSymbol();
        String[] input = in.nextLine().split(" ");
        if (input.length != 3) {
            printErrorMessage("Invalid input");
            return;
        }
        try {
            transactionService.transferMoney(
                    transactionService.getUserById(Integer.parseInt(input[0])),
                    transactionService.getUserById(Integer.parseInt(input[1])),
                    Integer.parseInt(input[2])
            );
            printOkMessage("The transfer is completed");
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        } catch (UserNotFoundException e) {
            printErrorMessage("User not found");
        } catch (IllegalTransactionException e) {
            printErrorMessage("Illegal transaction");
        }
    }

    private void viewUserTransactions(Scanner in) {
        System.out.println("Enter user ID:");
        printInputSymbol();
        try {
            User user = transactionService.getUserById(Integer.parseInt(in.nextLine()));
            for (Transaction transaction : transactionService.getUserTransactions(user).toArray()) {
                printOkMessage("To " + transaction.getRecipient().getName() +
                        "(id = " + transaction.getRecipient().getId() + ") " +
                        transaction.getAmount() +
                        " with id = " + transaction.getId()
                );
            }
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        } catch (UserNotFoundException e) {
            printErrorMessage("User not found");
        }
    }

    private void viewUserBalances(Scanner in) {
        System.out.println("Enter user ID:");
        printInputSymbol();
        try {
            User user = transactionService.getUserById(Integer.parseInt(in.nextLine()));
            printOkMessage(user.getName() + " - " + transactionService.getUserBalance(user));
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        } catch (UserNotFoundException e) {
            printErrorMessage("User not found");
        }
    }

    private void addUser(Scanner in) {
        System.out.println("Enter a user name and a balance:");
        printInputSymbol();
        String[] input = in.nextLine().split(" ");
        if (input.length != 2) {
            printErrorMessage("Invalid input");
            return;
        }
        try {
            User user = transactionService.addUser(input[0], Integer.parseInt(input[1]));
            printOkMessage("User with id = " + user.getId() + " is added");
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        }
    }

    private void help() {
        int i = 1;
        printHelpLine(i++, "Add a user");
        printHelpLine(i++, "View user balances");
        printHelpLine(i++, "Perform a transfer");
        printHelpLine(i++, "View all transactions for a specific user");
        if (isDev) {
            i = printDevHelp(i);
        }
        printHelpLine(i, "Finish execution");
    }

    private int scanInputCode(Scanner in) {
        help();
        printInputSymbol();
        try {
            return Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            printErrorMessage("Invalid input");
        }
        return -1;
    }

    private int printDevHelp(int number) {
        printHelpLine(number++, "DEV - remove a transfer by ID");
        printHelpLine(number++, "DEV - check transfer validity");
        return number;
    }

    private void printHelpLine(int number, String line) {
        System.out.println(number + ". " + line);
    }

    private void printErrorMessage(String message) {
        System.out.println("\u001B[31mError: " + message + "\u001B[0m");
    }

    private void printOkMessage(String message) {
        System.out.println("\u001B[36m" + message + "\u001B[0m");
    }

    private void printInputSymbol() {
        System.out.print("\u001B[33m" + inputSymbol + "\u001B[0m");
    }

    private void printSeparator() {
        System.out.println("---------------------------------------------------------");
    }
}
