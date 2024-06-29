package com.deploy.deploy;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AddingFinalProjectName {
    
    public void AddingFinalName(String pomPath,String name){
        String PathToPom = pomPath + "pom.xml";
        System.out.println("*********************************************************** " + PathToPom);
        try {
            List<String> allLines = Files.readAllLines(Path.of(PathToPom));
            List<String> newFileCodes = new ArrayList<>();
            int index = 0;
//            int isFile
            for(String singleLine: allLines){
                newFileCodes.add(singleLine);
                if(singleLine.trim().equals("<build>")){
                    if(allLines.get(index + 1).contains("<finalName>")){
                        
                    }else{
                        newFileCodes.add( "<finalName>"+name+"</finalName>");
                    }
                }
                index++;
            }
            
            FileWriter writter = new FileWriter(PathToPom);
            for(String singleLine: newFileCodes){
                writter.write(singleLine+System.lineSeparator());
            }
            
            writter.close();
            System.out.println("fininsh");

        } catch (Exception e) {
            System.out.println(" this is the actual error " +e);
        }
        
        
    }
    
}
