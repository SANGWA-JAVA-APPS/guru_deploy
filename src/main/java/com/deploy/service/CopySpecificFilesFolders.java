/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CopySpecificFilesFolders {

    List<String> allerrors = new ArrayList<>();

    public List<String> copyFileOrFolder(String filesToCopy, String copysourcePath, String copyDestinationPath) {
//        String basicLocation = System.getProperty("user.home");
        String[] filesToCopyList = filesToCopy.split(",");
        String finalDeployLocation = copyDestinationPath;
        CopySpecificFolderService specificFolder = new CopySpecificFolderService();

        for (String singleFileToCopy : filesToCopyList) {
            File fullCopyFrom = new File(copysourcePath + singleFileToCopy);
            if (fullCopyFrom.isDirectory()) {

                specificFolder.copySpecificFolder(String.valueOf(fullCopyFrom), finalDeployLocation);
                System.out.println("------------------------------ from " + fullCopyFrom + " ******** to: " + finalDeployLocation);
                System.out.println("-------------------------------it is directory full path fromDir:" + fullCopyFrom);
            } else {
                try {
                    specificFolder.copySpecificFile(copysourcePath + singleFileToCopy, finalDeployLocation + EtractFileNameOnly(singleFileToCopy));
                    System.out.println("\n\n------------------- file " + copysourcePath + singleFileToCopy + " ***********");
                    System.out.println("------------------- as " + finalDeployLocation + EtractFileNameOnly(singleFileToCopy) + " ***********");
                } catch (Exception e) {
                    System.out.println(e);
                    allerrors.add(e.toString());
                }
            }

        }
        return allerrors;
    }

    private String EtractFileNameOnly(String singleFileToCopy) {
        String lastname = "";
        String fileNameOnlyFromPath[] = singleFileToCopy.split("/");
        if (fileNameOnlyFromPath.length > 1) {
            int length = fileNameOnlyFromPath.length - 1;
            lastname = fileNameOnlyFromPath[length];
            System.out.println("------------------Only the file name is: " + lastname);
        } else {
            return singleFileToCopy;
        }
        return lastname;
    }
}
