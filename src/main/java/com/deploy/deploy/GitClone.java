package com.deploy.deploy;

import java.io.File;
import java.nio.file.Path;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
public class GitClone {

    
    public void GitClone(String path,String token,String repo_name) {
        String repoUrl = "https://github.com/cheriValensIradukunda/" + repo_name + ".git";
        EmptingFolder emptingFolder = new EmptingFolder();
        File cloneDirectory = new File(path);
        String username = "cheriValensIradukunda";

        if(cloneDirectory.isDirectory()){
            String[] files = cloneDirectory.list();
            if(files.length> 0){
                Path directory = cloneDirectory.toPath();
                emptingFolder.DeleteFile(directory);//delete the existing
            }
        }
        try {
            System.out.println("Cloning repository from " + repoUrl + " to " + cloneDirectory.getAbsolutePath());

            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(cloneDirectory)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, token))
                    .call();
            System.out.println(path);
            System.out.println("Repository cloned successfully.");

            
            
            //change the pom file
        } catch (GitAPIException e) {
            System.err.println("Exception occurred while cloning repository: " + e.getMessage());
        }
    }
    
}
