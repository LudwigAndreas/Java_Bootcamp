package controller;

import model.FileSimilarityResolver;

import java.io.*;
import java.util.TreeSet;

public class ConsoleMenu {
    public enum Profile {
        DEV,
        PROD
    }

    private FileWriter fileWriter;

    public ConsoleMenu(File output) throws IOException {
        fileWriter = new FileWriter(output);
    }

    public void run(String args[]) {
        if (args.length != 2) {
            printErrorMessage("Wrong number of arguments!");
            printHelpMessage();
            return;
        }
        String filePath1 = args[0];
        String filePath2 = args[1];
        try {
            String out = String.format("%.3f", FileSimilarityResolver.resolve(filePath1, filePath2));

            TreeSet<String> dictionary = FileSimilarityResolver.getDictionary(filePath1, filePath2);
            for (String word : dictionary) {
                fileWriter.write(word + "\n");
            }
            fileWriter.close();
            printOkMessage("Similarity = " + out.substring(0, out.length() - 1));
        } catch (IOException e) {
            printErrorMessage("File not found!");
        }
    }

    private void printHelpMessage() {
        System.out.print("Usage: ");
        System.out.println("java Program FILE1 FILE2");
        System.out.println("Check files similarity.");
        System.out.println();
    }

    private void printErrorMessage(String message) {
        System.out.println("\u001B[31mError: " + message + "\u001B[0m");
    }

    private void printOkMessage(String message) {
        System.out.println("\u001B[36m" + message + "\u001B[0m");
    }

    private void printSeparator() {
        System.out.println("---------------------------------------------------------");
    }
}
