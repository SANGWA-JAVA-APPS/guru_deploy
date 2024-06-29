package com.deploy.deploy;

import com.deploy.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MavenBuild {

    private static List<String> allErrors = null;

    public MavenBuild() {
        allErrors = new ArrayList<>();
    }

    public static List<String> buildProject(String projectDir, String path,String command,String language) {
        runMavenBuild(projectDir, path, command, language);
        allErrors.add("\n\n\n-------------- MAVEN BUILD RESULTS ---------------------\n");
        System.out.println("--------------- MAVEN BUILD RESULTS ---------------------\n");
        return allErrors;
    }

    public static List<String> runMavenBuild(String projectDir, String path, String dbCommand,String language) {
        if(language.equals("java")){
            AddingFinalProjectName finalProjectName = new AddingFinalProjectName();
            finalProjectName.AddingFinalName(projectDir, path);
        }
        
//        String mvncommand = (Util.isFSBS) ? "mvn" : "mvn.cmd";
//        String[] command = {mvncommand, "clean", "install"};
        String[] command = dbCommand.split(" ");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(Paths.get(projectDir).toFile());
        Process process = null;

        try {
            process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    allErrors.add("\n"+line);
                }
            }
        } catch (IOException e) {

            allErrors.add(e.toString()); //-------------catch error
            e.printStackTrace();
        }
        try {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    System.out.println("\n"+line);
                }
            }
        } catch (IOException e) {

            allErrors.add(e.toString());//-------------catch error
            e.printStackTrace();
        }
        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException ex) {
            allErrors.add("waiting process faild " + String.valueOf(exitCode));
            //-------------catch error
            Logger.getLogger(MavenBuild.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allErrors;
    }
}
