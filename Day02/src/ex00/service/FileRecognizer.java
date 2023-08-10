package service;

import model.FilePattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FileRecognizer {

    private List<FilePattern> patterns;

    public FileRecognizer(List<FilePattern> patterns) {
        this.patterns = patterns;
    }

    public void setPatterns(List<FilePattern> patterns) {
        this.patterns = patterns;
    }

    public String recognize(File file) throws FileNotFoundException {
        for (FilePattern pattern : patterns) {
            if (pattern.match(file)) {
                return pattern.getName();
            }
        }
        return "Unknown";
    }
}
