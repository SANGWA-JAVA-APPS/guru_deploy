package com.deploy.deploy;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class EmptingFolder {

    public void DeleteFile(Path directory) {
//        Path directory = Paths.get("/opt/tomcat/webapps/Github");

        try {
            // Command to kill all git.exe processes
            String command = "taskkill /F /IM git.exe";
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println("All git processes terminated.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (Files.isDirectory(directory)) {

            try {
                // Empty the directory
                if (emptyDirectory(directory)) {
                    System.out.println("The directory has been emptied.");
                } else {
                    System.out.println("Failed to empty the directory.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while emptying the directory.");
                e.printStackTrace();

            }
        } else {
            System.out.println("The provided path is not a directory.");
        }
    }

    public static boolean emptyDirectory(Path directory) throws IOException {
        try (Stream<Path> files = Files.walk(directory)) {
            files.sorted(Comparator.reverseOrder())
                    .filter(path -> !path.equals(directory))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + path);
                            e.printStackTrace();

                        }
                    });
        } catch (NoSuchFileException e) {
            System.err.println("No such file or directory: ");
        } catch (AccessDeniedException e) {
            System.err.println("Access denied: ");
        } catch (IOException e) {
            System.err.println("I/O error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while traversing the directory.");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
