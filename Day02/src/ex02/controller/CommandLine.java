package controller;

import java.nio.file.*;
import java.io.IOException;

import java.util.Scanner;
import java.util.stream.Stream;

public class CommandLine {
    private static Path currentDirectory;

    public CommandLine(String startDirectory) {
        currentDirectory = Paths.get(startDirectory);

        if (Files.exists(currentDirectory) && Files.isDirectory(currentDirectory)) {
            startCLI();
        } else {
            System.out.println("Start directory does not exist or is not a directory.");
        }
    }

    private static void startCLI() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.println(currentDirectory.toAbsolutePath());

        while (!exit) {
            System.out.print(" -> ");
            String input = scanner.nextLine();
            String[] commandParts = input.split(" ");

            String command = commandParts[0];
            switch (command) {
                case "ls":
                    listFilesAndDirectories();
                    break;
                case "cd":
                    if (commandParts.length > 1) {
                        navigateToDirectory(commandParts[1]);
                        System.out.println(currentDirectory.toAbsolutePath());
                    } else {
                        System.out.println("Usage: cd <directory_name>");
                    }
                    break;
                case "mv":
                    if (commandParts.length > 2) {
                        moveFile(commandParts[1], commandParts[2]);
                    } else {
                        System.out.println("Usage: mv <source_file> <destination_file>");
                    }
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
        scanner.close();
    }

    private static void listFilesAndDirectories() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory)) {
            for (Path path : stream) {
                String fileSize = "0";
                if (Files.isDirectory(path))
                    fileSize = String.format("%.3f", getDirectorySize(path) * (1.0 / 1024));
                else {
                    fileSize = String.format("%.3f", Files.size(path) * (1.0 / 1024));
                }
                System.out.println(
                        path.getFileName() + " " + fileSize.substring(0, 4) + " KB"
                );

            }
        } catch (ReadOnlyFileSystemException e) {
            System.out.println("This file is Read only");
        } catch (IOException e) {
            System.out.println("IOException, please make sure that you have right to do it");
        }
    }

    private static long getDirectorySize(Path path) {
        long size = 0;

        try (Stream<Path> walk = Files.walk(path)) {
            size = walk
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();
        } catch (IOException e) {
            System.out.println("Error while calculating directory size.");
        }
        return size;
    }

    private static void navigateToDirectory(String directoryName) {
        Path newDirectory = currentDirectory.resolve(directoryName);
        if (Files.isDirectory(newDirectory)) {
                currentDirectory = newDirectory.normalize();
        } else {
            System.out.println("Directory not found: " + directoryName);
        }
    }

    private static void moveFile(String sourceFileName, String destinationFileName) {
        Path sourcePath = currentDirectory.resolve(sourceFileName);
        Path destinationPath = currentDirectory.resolve(destinationFileName);

        if (Files.isDirectory(destinationPath))
            destinationPath = destinationPath.resolve(destinationPath + "/" + sourceFileName);
        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (ReadOnlyFileSystemException e) {
            System.out.println("This file is Read only");
        } catch (IOException e) {
            System.out.println("IOException, please make sure that application have right to do it");
        }
    }
}
