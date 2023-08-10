package service;

import model.FilePattern;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SignionsParser {

    private SignionsParser() {}

    public static List<FilePattern> parse(FileInputStream fis) {
        List<FilePattern> filePatterns = new ArrayList<>();
        Scanner scanner = new Scanner(fis);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(", ");
            String name = parts[0];
            String signature = parts[1];
            filePatterns.add(new FilePattern(name, decodeHexString(signature)));
        }

        return filePatterns;
    }

    private static byte[] decodeHexString(String hexString) {
        String[] hexStringParts = hexString.split(" ");

        byte[] bytes = new byte[hexStringParts.length];
        for (int i = 0; i < hexStringParts.length; ++i) {
            if (hexStringParts[i].length() != 2) {
                throw new IllegalArgumentException(
                        "Invalid hexadecimal String supplied.");
            }
            bytes[i] = hexToByte(hexStringParts[i]);
        }
        return bytes;
    }

    private static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }

}
