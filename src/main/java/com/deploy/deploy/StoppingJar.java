package com.deploy.deploy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StoppingJar {

    public static void main(String[] args) {
        int port = 9000; // The port your application is using
        try {
            // Find the PID of the process using the port
            String[] findProcessCommand = {"/bin/sh", "-c", "lsof -i :" + port + " | grep LISTEN | awk '{print $2}'"};
            Process findProcess = Runtime.getRuntime().exec(findProcessCommand);
            BufferedReader input = new BufferedReader(new InputStreamReader(findProcess.getInputStream()));
            String pid = input.readLine();
            input.close();
            
            if (pid != null && !pid.isEmpty()) {
                // Kill the process using the PID
                String[] killCommand = {"/bin/sh", "-c", "kill -9 " + pid};
                Process killProcess = Runtime.getRuntime().exec(killCommand);
                killProcess.waitFor();
                System.out.println("Process on port " + port + " terminated successfully.");
            } else {
                System.out.println("No process found using port " + port);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
