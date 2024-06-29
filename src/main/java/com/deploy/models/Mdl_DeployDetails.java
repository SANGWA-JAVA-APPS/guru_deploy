package com.deploy.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gurudeploy_details")
public class Mdl_DeployDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String repo_name;
    String username;
    String token;

    //specify where project will be clonned
    String clone_location_win;
    String clone_location_linux;

    //the final name of the jat, usually this will the same as the one speficfied in the pom
    @Column(unique = true)
    String project_final_name;

    //specify where project will be deployed and coppied to
    String final_deploy_location_linux;
    String final_deploy_location_win;

    //specify where the coppied files will be moved from
    String coppy_from_win;
    String coppy_from_linux;
    //specify if it will copy all_files or copy specific files, specific folders
    String files_to_coppy;
    //The port will be provided, and the system will help to check if it has in use by another application
    @Column(unique = true)
    int port;
    /*  These are the command that the users wants to have the system run, for eg: "mvn clean install" or "npm run build"
        The system will be able to deploy any type of applocation  */
    String commands_linux;
    String commands_win;
    //(open,closed) these are the values on status to show if project is ongoing or closed
    String status;
    //This by defaul will be blank and it will have records when the while loop runs from com.deploy.service the method is  cloneBuildAndCopy
    String date_time_project_start;
    //this by defaul will be blank and it will have records when the while loop runs from com.deploy.service the method is  cloneBuildAndCopy
    String date_time_last_commit;
//    and it will have records when the while loop runs from com.deploy.service the method is  cloneBuildAndCopy
    String date_time_take_war;
    @Column(columnDefinition = "MEDIUMTEXT")
    String deploy_logs;
    //project language
    String Language;

}
