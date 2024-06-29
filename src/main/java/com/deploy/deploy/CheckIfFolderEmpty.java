/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.deploy;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
public class CheckIfFolderEmpty {

    public static boolean checkIfEmpty(String path) {
        // Specify the path to the directory
        Path directoryPath = Paths.get(path);
        
        System.out.println("------------------------------- " + directoryPath + " -----------------------------");

        // Check if the directory is empty
        return isDirectoryEmpty(directoryPath);
    }

    public static boolean isDirectoryEmpty(Path directory) {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
