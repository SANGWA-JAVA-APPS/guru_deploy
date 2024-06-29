package com.deploy.service;

import com.deploy.controller.history.GithubPushHistoryController;
import com.deploy.deploy.CheckIfFolderEmpty;
import com.deploy.deploy.GitClone;
import com.deploy.deploy.MavenBuild;
import com.deploy.models.Mdl_DeployDetails;
import com.deploy.models.Mdl_forPush;
import com.deploy.repository.Repo_DeployDetails;
import com.deploy.util.PubConf;
import static com.deploy.util.PubConf.GITHUB_API_URL;
import com.deploy.util.Util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Component
public class DeployStarter {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebServerApplicationContext webServerApplicationContext;

    @Autowired
    private Repo_DeployDetails deployDetailsRepo;
    CopySpecificFilesFolders copyFilesAndFolders = new CopySpecificFilesFolders();
    public static List<String> allbuildErrors = new ArrayList<>();

    public void startDeplument() {
        System.out.println("........The deplyment has started ....");
    }

    @Async("asynchrExecutor")
    public void cloneBuildAndCopy() {
        System.out.println(".......Started the loop ..........");
        while (true) {
            PubConf.runStatus = System.currentTimeMillis();
            //This retries the data from the databasse about the Github commit history
            List<Mdl_DeployDetails> AllGithubCommitsdetails = deployDetailsRepo.getActiveProjects("open");

            for (Mdl_DeployDetails deploymentDetails : AllGithubCommitsdetails) {
                //about githubs
                String REPO_NAME = deploymentDetails.getRepo_name();
                String REPO_OWNER = deploymentDetails.getUsername();
                String ACCESS_TOKEN = deploymentDetails.getToken();
//                String basicLocation = System.getProperty("user.home");
                String projectDir = Util.isFSBS ? deploymentDetails.getClone_location_linux() : deploymentDetails.getClone_location_win();

                //about paths
                String destinationFilePath = Util.isFSBS ? deploymentDetails.getFinal_deploy_location_linux() : deploymentDetails.getFinal_deploy_location_win();
                String REPO_URL = "https://github.com/" + REPO_OWNER + "/" + REPO_NAME + ".git";
                String copyFrom = Util.isFSBS ? deploymentDetails.getCoppy_from_linux() : deploymentDetails.getCoppy_from_win();
                //about files
                String filesToCopy = deploymentDetails.getFiles_to_coppy();
                String lastPushDate = deploymentDetails.getDate_time_last_commit();
                String currentLastGithubPushDate = "";
                String command = Util.isFSBS ? deploymentDetails.getCommands_linux() : deploymentDetails.getCommands_win();
                String language = deploymentDetails.getLanguage();

                int projectPort = webServerApplicationContext.getWebServer().getPort();

                try {
                    currentLastGithubPushDate = fetchLastPushDate(REPO_OWNER, REPO_NAME, ACCESS_TOKEN);
                } catch (IOException e) {
                    System.out.println(e);
                }

                if (!currentLastGithubPushDate.equals(lastPushDate)) {
                    lastPushDate = currentLastGithubPushDate;
                    if (!CheckIfFolderEmpty.checkIfEmpty(projectDir)) {//if not empty
                        //pull insteadnt, StandardCharsets.UTF_8);
                        Mdl_forPush path = new Mdl_forPush();
                        path.setPath("/home/iradukunda/NetBeansProjects/With Sangwa/GithubRead/Auto_deploy_container"
                                + "/new/guru_deploy/target/guru_deploy.jar");
                        path.setProjectPort(projectPort);
                        if (Util.isFSBS) {
                            path.setCloneDir(deploymentDetails.getClone_location_linux());
                        } else {
                            path.setCloneDir(deploymentDetails.getClone_location_win());
                        }
//                        System.out.println("-----------------------------------------port for start "+url);
                        String getForStart = restTemplate.postForObject("http://localhost:8081/", path, String.class);
                        System.out.println("------------------------------- restart running " + getForStart + "----");
                        //call terminator to stop me

//                        PullFromGit pull = new PullFromGit(REPO_NAME, ACCESS_TOKEN, REPO_URL, projectDir);
                    } else {
                        System.out.println("--------------Started cloning");
                        GitClone cloning = new GitClone();
                        cloning.GitClone(projectDir, ACCESS_TOKEN, REPO_NAME);
                    }
                    MavenBuild projectBuild = new MavenBuild();
                    allbuildErrors = projectBuild.buildProject(projectDir, deploymentDetails.getProject_final_name(), command, language);
                    List<String> copyErrors = copyFilesAndFolders.copyFileOrFolder(filesToCopy, copyFrom, destinationFilePath);
                    allbuildErrors.addAll(copyErrors);
                    deploymentDetails.setDate_time_last_commit(lastPushDate);
                    deploymentDetails.setDeploy_logs(String.valueOf(allbuildErrors));

                    deployDetailsRepo.save(deploymentDetails);

                } else {
                    System.out.println("False");
                }

            }
            try {
                Thread.sleep(6000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GithubPushHistoryController.class.getName()).log(Level.SEVERE, null, ex);
                allbuildErrors.add(ex.toString());
                //-------------catch error
            }
        }

    }

    private static String fetchLastPushDate(String owner, String repo, String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("%s/repos/%s/%s/commits", GITHUB_API_URL, owner, repo);
        System.out.println();
        System.out.println("************* full url " + url);
        System.out.println();
        Request request = new Request.Builder()
                .url(url).header("Authorization", "token " + accessToken).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode commits = objectMapper.readTree(responseBody);

            if (commits.isArray() && commits.size() > 0) {
                JsonNode lastCommit = commits.get(0); // Get the latest commit
                String commitDate = lastCommit.get("commit").get("author").get("date").asText();
                return convertGithubDateTime(commitDate);
            }

            return null; // No commits found
        }
    }

    private static String convertGithubDateTime(String githubDateTime) {
        Instant instant = Instant.parse(githubDateTime);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.toString();
    }

}
