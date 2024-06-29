/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
public class CopyAllFolderContentsService {

    public static void copyFolder(Path sourceDir, Path targetDir) throws IOException {

//        if (!Files.exists(Paths.get("C:\\Users\\HP\\Documents\\test\\source\\dest\\source"))) {
//            File lastDir = new File("C:\\Users\\HP\\Documents\\test\\source\\dest\\source");
//            lastDir.mkdir();
//        }
        Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = targetDir.resolve(sourceDir.relativize(dir));
                if (!Files.exists(targetPath)) {
                    Files.createDirectory(targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, targetDir.resolve(sourceDir.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
