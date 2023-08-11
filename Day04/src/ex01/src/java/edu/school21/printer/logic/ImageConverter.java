package edu.school21.printer.logic;

import java.awt.image.BufferedImage;

public class ImageConverter {
    private static char black = '.';
    private static char white = '.';
    private ImageConverter() {}

    public static String convertToAscii(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        StringBuilder asciiArt = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int gray = (int) (0.2989 * red + 0.5870 * green + 0.1140 * blue);
                char asciiChar = getAsciiChar(gray);
                asciiArt.append(asciiChar);
            }
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

    public static void setBlack(char black) {
        ImageConverter.black = black;
    }

    public static void setWhite(char white) {
        ImageConverter.white = white;
    }

    private static char getAsciiChar(int grayValue) {
//        char[] asciiChars = {'@', '#', '8', '&', 'o', ':', '*', '.', ' '};
        char[] asciiChars = {black, white};
        int index = grayValue * (asciiChars.length) / 255;
        return asciiChars[index];
    }
}
