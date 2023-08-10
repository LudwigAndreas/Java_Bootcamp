package controller;

import service.FileRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleMenu {
    public enum Profile {
        DEV,
        PROD
    }
    private static final String inputSymbol = "-> ";
    private boolean isDev = false;

    private FileRecognizer fileRecognizer;
    private OutputStream outputStream;

    public ConsoleMenu(Profile profile, FileRecognizer fileRecognizer, OutputStream outputStream) {
        if (profile == Profile.DEV) {
            isDev = true;
        }
        this.fileRecognizer = fileRecognizer;
        this.outputStream = outputStream;
    }

    public void run() {
        Scanner in = new Scanner(System.in);

        while (true) {
            printInputSymbol();
            String filePath = in.nextLine();
            if (filePath.equals("exit")) {
                finishExecution();
            } else {
                try {
                    String outputMessage = "";
                    if (isDev) {
                        outputMessage += filePath + " -> ";
                    }
                    String checked = fileRecognizer.recognize(new File(filePath));
                    if (checked.equals("Unknown")) {
                        printErrorMessage("Unknown file type!");
                    } else {
                        outputMessage += checked;
                        outputMessage += "\n";
                        outputStream.write(outputMessage.getBytes(StandardCharsets.UTF_8));
                        printOkMessage("PROCESSED");
                    }
                }catch (FileNotFoundException e) {
                    printErrorMessage("File not found!");
                } catch (SecurityException e) {
                    printErrorMessage("Access denied!");
                } catch (IllegalArgumentException e) {
                    printErrorMessage("Illegal argument!");
                } catch (NullPointerException e) {
                    printErrorMessage("Null pointer exception!");
                } catch (Exception e) {
                    printErrorMessage("Unknown error!");
                }
            }
        }
    }

    private void finishExecution() {
        printOkMessage("Bye!");
        System.exit(0);
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
