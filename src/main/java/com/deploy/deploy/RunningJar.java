package com.deploy.deploy;

public class RunningJar {

    public static void main(String[] args) {
        
        System.out.println("...............Runing a jar ");
//         new RunningJar();
        try {
            
            Thread.sleep(10000);
            
        } catch (Exception e) {
            System.out.println("------------------------------- fail to run"+ e);
            
        }
        System.out.println("------------------ end of running a jar");
    }

    public RunningJar() {
        
        String path = "/home/iradukunda/Documents/Githubs/NewFolder/project2/valens_test.jar";
        ProcessBuilder runJar = new ProcessBuilder("java","-jar",path);
        
        runJar.inheritIO();
        
        try {
            Process process = runJar.start();
            System.out.println("running");
            
            int exitCode = process.waitFor();
            
            System.out.println("process exited with this code" + exitCode);
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    
}
