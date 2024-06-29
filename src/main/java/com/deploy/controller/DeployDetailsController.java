package com.deploy.controller;

import com.deploy.dto.CustomErrorMessage;
import com.deploy.dto.DeployParamsDto;
import com.deploy.dto.DeploymentAllFields;
import com.deploy.models.Mdl_DeployDetails;
import com.deploy.repository.Repo_DeployDetails;
import com.deploy.util.PubConf;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployDetailsController {

    @Autowired
    private Repo_DeployDetails deployDetailsRepo;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H-mm-ss");
    String nowString = now.format(formatter);

    @GetMapping("/greet")
    public String welcome() {
        return "You are welcome to guru Deploy";
    }

    //Registering the deployment details
    @PostMapping("/")
    public String saveRepository(@RequestBody Mdl_DeployDetails data) {
        data.setDate_time_project_start(nowString);
        if (deployDetailsRepo.getPort(data.getPort()) == null) {
            deployDetailsRepo.save(data);
            return "done";
        } else {
            return "port is arleady taken";
        }
    }

    @PostMapping("/make/newproject")
    public ResponseEntity<?> saveProject(@RequestBody DeployParamsDto deploy) {

        Mdl_DeployDetails mdl_DeployDetails = new Mdl_DeployDetails();
        if (deployDetailsRepo.countByPort(deploy.getDeployPort()) > 0) {
            return new ResponseEntity<>(new CustomErrorMessage("The port is already taken"), HttpStatus.FOUND);
        } else if (deployDetailsRepo.countByProjectFinalName(deploy.getProject_final_name()) > 0) {
            return new ResponseEntity<>(new CustomErrorMessage("The Project final name should be unique"), HttpStatus.FOUND);
        } else {
            mdl_DeployDetails.setRepo_name(deploy.getRepo_name());
            mdl_DeployDetails.setUsername(deploy.getUsername());
            mdl_DeployDetails.setToken(deploy.getToken());
            mdl_DeployDetails.setClone_location_win(deploy.getClone_location_win());
            mdl_DeployDetails.setClone_location_linux(deploy.getClone_location_linux());
            mdl_DeployDetails.setProject_final_name(deploy.getProject_final_name());
            mdl_DeployDetails.setFinal_deploy_location_linux(deploy.getFinal_deploy_location_linux());
            mdl_DeployDetails.setFinal_deploy_location_win(deploy.getFinal_deploy_location_win());
            mdl_DeployDetails.setCoppy_from_win(deploy.getCommands_win());
            mdl_DeployDetails.setCoppy_from_linux(deploy.getCoppy_from_linux());
            mdl_DeployDetails.setFiles_to_coppy(deploy.getFiles_to_coppy());
            mdl_DeployDetails.setPort(deploy.getDeployPort());
            mdl_DeployDetails.setCommands_linux(deploy.getCommands_win());
            mdl_DeployDetails.setCommands_win(deploy.getCommands_win());
            mdl_DeployDetails.setStatus(deploy.getStatus());
            mdl_DeployDetails.setDate_time_project_start(nowString);
            return new ResponseEntity<>(deployDetailsRepo.save(mdl_DeployDetails), HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public List<DeploymentAllFields> getAllDeployedDetails() {
        List<Mdl_DeployDetails> deploys = deployDetailsRepo.findAll();
        return deploys.stream().map(deploy -> new DeploymentAllFields(
                deploy.getId(),
                deploy.getRepo_name(),
                deploy.getUsername(),
                deploy.getToken(),
                deploy.getClone_location_linux(),
                deploy.getClone_location_win(),
                deploy.getProject_final_name(),
                deploy.getFinal_deploy_location_linux(),
                deploy.getFinal_deploy_location_win(),
                deploy.getCoppy_from_win(),
                deploy.getCoppy_from_linux(),
                deploy.getFiles_to_coppy(),
                deploy.getPort(),
                deploy.getCommands_linux(),
                deploy.getCommands_win(),
                deploy.getStatus(),
                deploy.getDate_time_project_start(),
                deploy.getDate_time_last_commit(),
                deploy.getDate_time_take_war(),
                deploy.getDeploy_logs(),
                deploy.getLanguage()
        )).collect(Collectors.toList());

    }

    @PutMapping("/{id}")
    public String updateDeployedDetails(@PathVariable Long id, @RequestBody Mdl_DeployDetails data) {
        Mdl_DeployDetails existingDetails = deployDetailsRepo.findById(id).orElse(null);
        data.setId(existingDetails.getId());
        existingDetails = data;
        deployDetailsRepo.save(existingDetails);
        return "done";
    }

    @DeleteMapping("/{id}")
    public String deleteDeployDetail(@PathVariable Long id) {
        deployDetailsRepo.deleteById(id);
        return "done";

    }

    @GetMapping("/checkForBasePath")
    public String update() {
        return System.getProperty("user.home");
    }
    @GetMapping("/check/runStatus")
    public long getRunStatus() {

        long deployMills = PubConf.runStatus;

        long CurrentMills = System.currentTimeMillis();
        long difference = CurrentMills - deployMills;
        long differenceMinutes = difference / (60 * 1000);
        System.out.println("The deploy millseconds: " + deployMills);
        System.out.println("The current mills: " + CurrentMills);

        long differenceSeconds = difference / 1000;
        return differenceSeconds;
    }
}
