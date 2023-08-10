package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileSimilarityResolver {

    private FileSimilarityResolver() {
    }

    private static List<String> readFileWords(String filePath) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.addAll(Arrays.asList(line.toLowerCase().split("[^a-zA-Z]+")));
            }
        }
        return words;
    }

    public static TreeSet<String> getDictionary(String fileA, String fileB) throws IOException {
        TreeSet<String> dictionary = new TreeSet<>();
        dictionary.addAll(readFileWords(fileA));
        dictionary.addAll(readFileWords(fileB));
        return dictionary;
    }

    public static double resolve(String fileA, String fileB) throws IOException {
        BufferedReader brA = new BufferedReader(new FileReader(fileA));
        BufferedReader brB = new BufferedReader(new FileReader(fileB));

        if (!brA.ready() && !brB.ready())
            return 1;
        else if (!brA.ready() || !brB.ready())
            return 0;

        TreeSet<String> dictionary = getDictionary(fileA, fileB);
        List<String> inputA = readFileWords(fileA);
        List<String> inputB = readFileWords(fileB);

        brA.close();
        brB.close();
        return calculateCosineSimilarity(inputA, inputB, dictionary);
    }

    private static List<Integer> occurrenceFrequency(List<String> vector, TreeSet<String> dictionary) {
        List<Integer> frequency = new ArrayList<>(dictionary.size());
        int i = 0;
        int count = 0;

        for (String dictElem : dictionary) {
            for (String inputElem : vector) {
                if (dictElem.equals(inputElem))
                    count++;
            }
            frequency.add(i, count);
            count = 0;
            i++;
        }
        return frequency;
    }

    private static double calculateCosineSimilarity(List<String> vec1, List<String> vec2, TreeSet<String> dictionary) {
        List<Integer> occur1 = occurrenceFrequency(vec1, dictionary);
        List<Integer> occur2 = occurrenceFrequency(vec2, dictionary);

        int numerator = calcNumerator(occur1, occur2, dictionary);
        double denominator = calcDenominator(occur1, occur2);
        return numerator / denominator;
    }

    private static double calcDenominator(List<Integer> occur1, List<Integer> occur2) {
        double denominator = 0;
        for (int i = 0; i < occur1.size(); i++) {
            denominator += Math.pow(occur1.get(i), 2);
        }
        denominator = Math.sqrt(denominator);
        double denominator2 = 0;
        for (int i = 0; i < occur2.size(); i++) {
            denominator2 += Math.pow(occur2.get(i), 2);
        }
        denominator2 = Math.sqrt(denominator2);
        return denominator * denominator2;
    }

    private static int calcNumerator(List<Integer> occur1, List<Integer> occur2, TreeSet<String> dictionary) {
        int numerator = 0;
        for (int i = 0; i < dictionary.size(); i++) {
            numerator += occur1.get(i) * occur2.get(i);
        }
        return numerator;
    }
}