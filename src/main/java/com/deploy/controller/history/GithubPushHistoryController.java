package com.deploy.controller.history;

import com.deploy.models.Mdl_DeployDetails;
import com.deploy.service.DeployStarter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deploy.repository.Repo_DeployDetails;

@RestController
@RequestMapping("/git")
public class GithubPushHistoryController {

    @Autowired
    private Repo_DeployDetails deployDetailsRepo;

    private static final String url = "";

    @Autowired
    DeployStarter deployService;

    public GithubPushHistoryController(DeployStarter deployService) {
        this.deployService = deployService;
    }

    @GetMapping("/check")
    public List<Mdl_DeployDetails> getAllActiveProjects() {
        return deployDetailsRepo.getActiveProjects("open");
    }

    @GetMapping("/clone")
    public List<String> cloneGithub() {
        List<String> allerrors = new ArrayList<>();
        List<Mdl_DeployDetails> deployDetailsList = deployDetailsRepo.getActiveProjects("open");

        deployService.startDeplument();//return
        deployService.cloneBuildAndCopy();// here the method just clones, builds and copies the files and folder the user want to be in the deployed location

        return allerrors = DeployStarter.allbuildErrors;
    }

}
