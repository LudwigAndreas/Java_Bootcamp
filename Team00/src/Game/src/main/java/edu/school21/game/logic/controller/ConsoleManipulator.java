package edu.school21.game.logic.controller;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleManipulator {

    static final InputStream in = System.in;

    public static void clearConsole() throws IOException, InterruptedException {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }


    public static char readControls() {
        while (true) {
            try {
                char c = (char) in.read();
                switch (c) {
                    case 'w':
                        return 'w';
                    case 'a':
                        return 'a';
                    case 's':
                        return 's';
                    case 'd':
                        return 'd';
                    case '9':
                        return '9';
                    case '8':
                        return '8';
                    default:
                        continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
