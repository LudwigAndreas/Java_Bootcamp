package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.awt.image.BufferedImage;

public class ImageConverter {
    private static Ansi.BColor black = Ansi.BColor.BLACK;
    private static Ansi.BColor white = Ansi.BColor.WHITE;
    private ImageConverter() {}

    public static void printAscii(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int gray = (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);
                cp.print(" ", Ansi.Attribute.CLEAR, Ansi.FColor.BLACK, getAsciiChar(gray));
            }
            cp.clear();
            cp.println("");
        }
    }

    public static void setBlack(String black) {
        ImageConverter.black = getColor(black);
    }

    public static void setWhite(String white) {
        ImageConverter.white = getColor(white);
    }

    private static Ansi.BColor getColor(String input) {
        switch (input) {
            case "RED":
                return Ansi.BColor.RED;
            case "GREEN":
                return Ansi.BColor.GREEN;
            case "YELLOW":
                return Ansi.BColor.YELLOW;
            case "BLUE":
                return Ansi.BColor.BLUE;
            case "MAGENTA":
                return Ansi.BColor.MAGENTA;
            case "CYAN":
                return Ansi.BColor.CYAN;
            case "WHITE":
                return Ansi.BColor.WHITE;
            case "BLACK":
                return Ansi.BColor.BLACK;
            default:
                System.err.println("Unknown color");
                System.exit(1);
                return null;
        }
    }

    private static Ansi.BColor getAsciiChar(int grayValue) {
//        char[] asciiChars = {'@', '#', '8', '&', 'o', ':', '*', '.', ' '};
        Ansi.BColor[] asciiChars = {black, white};
        int index = grayValue * (asciiChars.length) / 255;
        return asciiChars[index];
    }
}
