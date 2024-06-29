/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.service;

import com.deploy.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CopySpecificFolderService {

    public static void copySpecificFolder(String copyFrom, String copyTo) {

        try {
            new CopyAllFolderContentsService().copyFolder(Paths.get(copyFrom), Paths.get(copyTo));
            System.out.println("finish coppying");
        } catch (IOException ex) {
            Logger.getLogger(CopySpecificFolderService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void copySpecificFile(String source, String target) {
        try {
            Files.copy(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("fininsh coppying");
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }

}
