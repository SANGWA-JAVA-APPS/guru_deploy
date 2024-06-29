package com.deploy.deploy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyWar {

    /**
     * @param args the command line arguments
     */
    public void CoppyWar(String sourceFilePath, String destinationFilePath) {
        // Specify the source and destination file paths
//        String sourceFilePath = "/home/iradukunda/Documents/Githubs/NewFolder/target/valens_test-0.0.1-SNAPSHOT.war";
//        String destinationFilePath = "/opt/tomcat/webapps";

        // Copy the file
        copyFile(sourceFilePath, destinationFilePath);
    }
    public static void copyFile(String sourceFilePath, String destinationFilePath) {

        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationDir = Paths.get(destinationFilePath);
        Path destinationPath = destinationDir.resolve(sourcePath.getFileName());

        try {
            // Ensure the destination directory exists
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            // Copy the source file to the destination directory with overwrite option
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.err.println("Error copying file: " + e);
        }
    }
}
