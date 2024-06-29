/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.deploy;

import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
public class PullFromGit {

    public PullFromGit(String gitUsername, String gittPassword, String githubRepoUrl, String pclocalPath) {
        // Repository URL and local directory path
        String repoUrl = githubRepoUrl;
        String localPath = pclocalPath;

        // GitHub credentials
        String username = gitUsername;
        String password = gittPassword;

        // Pull from the repository
        try {
            File repoDir = new File(localPath);
            Git git;

            if (!repoDir.exists()) {
                // Clone the repository if it doesn't exist locally
                System.out.println("Pulling process started to get changes...");
                
                
                git = Git.cloneRepository()
                        .setURI(repoUrl)
                        .setDirectory(repoDir)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                        .call();
                
            } else {
                // Open the existing repository
                System.out.println("Opening existing repository...");
                git = Git.open(repoDir);
            }

            // Perform a pull operation
            System.out.println("Pulling from repository...");
            try {
                PullResult result = git.pull()
                    .setRebase(true)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                    .call();
                if (result.isSuccessful()) {
                    System.out.println("Pull successful.");
                } else {
                    System.out.println("Pull failed: " + result.toString());
                }
                
            } catch (GitAPIException e) {
                System.out.println("-------------------------- pulling output ------------------------");
                System.out.println("message for pull .....");
                System.out.println(e.toString());
            }
            

            

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred while pulling from the repository.");
        }
        
        System.out.println("-------------------------------------------------------------------------");

    }

}
